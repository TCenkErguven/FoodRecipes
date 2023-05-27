package com.foody.service;

import com.foody.dto.request.ActivateStatusRequestDto;
import com.foody.dto.request.ForgotPasswordRequestDto;
import com.foody.dto.request.LoginRequestDto;
import com.foody.dto.request.RegisterAuthRequestDto;
import com.foody.dto.response.ChangePasswordAuthResponseDto;
import com.foody.dto.response.RegisterAuthResponseDto;
import com.foody.dto.response.UpdateAuthResponsetDto;
import com.foody.exception.AuthManagerException;
import com.foody.exception.ErrorType;
import com.foody.manager.IUserManager;
import com.foody.mapper.IAddressMapper;
import com.foody.mapper.IAuthMapper;
import com.foody.rabbitmq.model.ForgotPasswordMailModel;
import com.foody.rabbitmq.producer.ForgotPasswordProducer;
import com.foody.rabbitmq.producer.RegisterMailProducer;
import com.foody.repository.IAuthRepository;
import com.foody.repository.entity.Address;
import com.foody.repository.entity.Auth;
import com.foody.repository.entity.enums.EStatus;
import com.foody.utility.CodeGenerator;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final RegisterMailProducer registerMailProducer;
    private final ForgotPasswordProducer forgotPasswordProducer;
    private final JwtTokenProvider jwtTokenProvider;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    public AuthService(IAuthRepository authRepository,
                       IUserManager userManager,
                       RegisterMailProducer registerMailProducer,
                       ForgotPasswordProducer forgotPasswordProducer,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder,
                       AddressService addressService){
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.registerMailProducer = registerMailProducer;
        this.forgotPasswordProducer = forgotPasswordProducer;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }

    public RegisterAuthResponseDto registerUser(RegisterAuthRequestDto dto){
        Boolean status = authRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail());
        if(status)
            throw new AuthManagerException(ErrorType.MAIL_AND_USERNAME_EXIST);
        Auth auth = IAuthMapper.INSTANCE.fromRegisterRequestDtoToAuth(dto);
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        userManager.registerUserProfile(IAuthMapper.INSTANCE.fromAuthToRegisterUserProfileResponseDto(auth));
        registerMailProducer.sendActivationCode(IAuthMapper.INSTANCE.fromAuthToRegisterMailModel(auth));
        return IAuthMapper.INSTANCE.fromAuthToRegisterResponseDto(auth);
    }

    public Boolean activateStatus(ActivateStatusRequestDto dto){
        Optional<Auth> optionalAuth = findById(dto.getId());
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }else if(optionalAuth.get().getActivationCode().equals(dto.getActivationCode())){
            optionalAuth.get().setStatus(EStatus.ACTIVE);
            update(optionalAuth.get());
            userManager.activateStatusUserProfile(dto.getId());
            return true;
        }
        throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }

    public String login(LoginRequestDto dto){
        Optional<Auth> optionalAuth = authRepository.findByUsername(dto.getUsername());
        if (optionalAuth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), optionalAuth.get().getPassword()))
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        else if(!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        return jwtTokenProvider.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole())
                .orElseThrow(() -> {throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
                });
    }

    public Boolean forgotPassword(ForgotPasswordRequestDto dto){
        Optional<Auth> optionalAuth = authRepository.findByEmail(dto.getEmail());
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            String password = UUID.randomUUID().toString();
            optionalAuth.get().setPassword(passwordEncoder.encode(password));
            update(optionalAuth.get());
            userManager.forgotPassword(IAuthMapper.INSTANCE.fromAuthToForgotPasswordUserProfileResponseDto(optionalAuth.get()));
            ForgotPasswordMailModel forgotPasswordMailModel = IAuthMapper.INSTANCE.fromAuthToForgotPasswordMailModel(optionalAuth.get());
            forgotPasswordMailModel.setPassword(password);
            forgotPasswordProducer.sendForgotPassword(forgotPasswordMailModel);
            return true;
        }else{
            if(optionalAuth.get().getStatus().equals(EStatus.DELETED))
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean changePassword(ChangePasswordAuthResponseDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setPassword(dto.getPassword());
        update(optionalAuth.get());
        return true;
    }

    public Boolean update(UpdateAuthResponsetDto dto){
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalAuth.get().getAddressId() == null){
            Address address = IAddressMapper.INSTANCE.fromUpdateAuthResponseDtoToAddressSave(dto);
            addressService.save(address);
            optionalAuth.get().setAddressId(address.getId());
        }else{
            Optional<Address> optionalAddress = addressService.findById(optionalAuth.get().getAddressId());
            addressService.update(IAddressMapper.INSTANCE.fromUpdateAuthResponseDtoToAddress(dto,optionalAddress.get()));
        }
        update(IAuthMapper.INSTANCE.fromUpdateAuthResponseDtoToAuth(dto,optionalAuth.get()));
        return true;
    }

    public Boolean deleteAuth(Long authId) {
        Optional<Auth> optionalAuth = findById(authId);
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setStatus(EStatus.DELETED);
        update(optionalAuth.get());
        return true;
    }

    public Boolean inactivateAuth(Long authId) {
        Optional<Auth> optionalAuth = findById(authId);
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        optionalAuth.get().setStatus(EStatus.INACTIVE);
        update(optionalAuth.get());
        return true;
    }
}
