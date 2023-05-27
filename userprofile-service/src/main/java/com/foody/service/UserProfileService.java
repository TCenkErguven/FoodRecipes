package com.foody.service;

import com.foody.dto.request.ChangePasswordRequestDto;
import com.foody.dto.request.UpdateCommentUsernameRequestDto;
import com.foody.dto.request.UpdateUserProfileRequestDto;
import com.foody.dto.request.UserProfileMailRequestDto;
import com.foody.dto.response.FavoriteCategoryAlerterResponseDto;
import com.foody.dto.response.ForgotPasswordUserProfileResponseDto;
import com.foody.dto.response.RegisterUserProfileResponseDto;
import com.foody.dto.response.UserProfileResponseDto;
import com.foody.exception.ErrorType;
import com.foody.exception.UserProfileManagerException;
import com.foody.manager.IAuthManager;
import com.foody.manager.ICommentManager;
import com.foody.manager.IRecipeManager;
import com.foody.mapper.IUserProfileMapper;
import com.foody.rabbitmq.model.FavoriteRecipeAddedNotificationModel;
import com.foody.rabbitmq.producer.FavoriteRecipeAddedNotificationProducer;
import com.foody.repository.IUserProfileRepository;
import com.foody.repository.entity.UserProfile;
import com.foody.repository.entity.enums.EStatus;
import com.foody.utility.JwtTokenProvider;
import com.foody.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final IAuthManager authManager;
    private final ICommentManager commentManager;
    private final IRecipeManager recipeManager;
    private final FavoriteRecipeAddedNotificationProducer favoriteRecipeAddedNotificationProducer;
    public UserProfileService(IUserProfileRepository userProfileRepository,
                              JwtTokenProvider jwtTokenProvider,
                              PasswordEncoder passwordEncoder,
                              IAuthManager authManager,
                              ICommentManager commentManager,
                              IRecipeManager recipeManager,
                              FavoriteRecipeAddedNotificationProducer favoriteRecipeAddedNotificationProducer){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.commentManager = commentManager;
        this.recipeManager = recipeManager;
        this.favoriteRecipeAddedNotificationProducer = favoriteRecipeAddedNotificationProducer;
    }

    public Boolean registerUserProfile(RegisterUserProfileResponseDto dto) {
        UserProfile userProfile = IUserProfileMapper.INSTANCE.fromRegisterUserProfileRequestDto(dto);
        save(userProfile);
        return true;
    }

    public Boolean activateStatusUserProfile(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId);
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setStatus(EStatus.ACTIVE);
        update(optionalUserProfile.get());
        return true;
    }

    public Boolean forgotPassword(ForgotPasswordUserProfileResponseDto dto){
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(dto.getAuthId());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setPassword(dto.getPassword());
        update(optionalUserProfile.get());
        return true;
    }

    public Boolean changePassword(String token, ChangePasswordRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        if(passwordEncoder.matches(dto.getPassword(), optionalUserProfile.get().getPassword())){
            String newPass = passwordEncoder.encode(dto.getNewPassword());
            optionalUserProfile.get().setPassword(newPass);
            update(optionalUserProfile.get());
            authManager.changePasswordAuth(IUserProfileMapper.INSTANCE.fromUserProfileToChangePasswordAuthResponseDto(optionalUserProfile.get()));
            return true;
        }
        throw new UserProfileManagerException(ErrorType.PASSWORD_ERROR);
    }

    public UserProfile update(String token, UpdateUserProfileRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        String oldUsername = optionalUserProfile.get().getUsername();
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        update(IUserProfileMapper.INSTANCE.fromUpdateUserProfileRequestDtoToUserProfile(dto,optionalUserProfile.get()));
        authManager.updateAuth(IUserProfileMapper.INSTANCE.fromUserProfileToUpdateAuthRequestDto(optionalUserProfile.get()));
        if(!oldUsername.equals(optionalUserProfile.get().getUsername())){
            UpdateCommentUsernameRequestDto updateComment = UpdateCommentUsernameRequestDto.builder()
                    .newUsername(optionalUserProfile.get().getUsername())
                    .userId(optionalUserProfile.get().getId())
                    .build();
            commentManager.updateCommentUsername(updateComment);
        }
        return optionalUserProfile.get();
    }

    public Boolean deleteAccount(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setStatus(EStatus.DELETED);
        commentManager.removeAllUserCommentsAndPoints(IUserProfileMapper.INSTANCE.userProfileToremoveCommentAndPointRequestDto(optionalUserProfile.get()));
        update(optionalUserProfile.get());
        authManager.deleteAuth(optionalUserProfile.get().getAuthId());
        return true;
    }

    public Boolean inactivate(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setStatus(EStatus.INACTIVE);
        update(optionalUserProfile.get());
        authManager.inactivateAuth(optionalUserProfile.get().getAuthId());
        return true;
    }

    public UserProfileResponseDto getUserProfileDto(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId);
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        return UserProfileResponseDto.builder()
                .username(optionalUserProfile.get().getUsername())
                .userId(optionalUserProfile.get().getId())
                .build();
    }

    public Boolean addFavoriteRecipe(String token, String recipeId){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        if(!recipeManager.doesRecipeExist(recipeId).getBody())
            throw new UserProfileManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalUserProfile.get().getFavoriteRecipes().forEach(recipe -> {
            if(recipe.equals(recipeId))
                throw new UserProfileManagerException(ErrorType.RECIPE_NOT_FOUND);
        });
        optionalUserProfile.get().getFavoriteRecipes().add(recipeId);
        //categoryId'yle donecek
        update(optionalUserProfile.get());
        return true;
    }

    public Boolean dropFavoriteRecipe(String token, String recipeId){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if(authId.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if(optionalUserProfile.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalUserProfile.get().getFavoriteRecipes().isEmpty())
            throw new UserProfileManagerException(ErrorType.NO_RECIPE_ON_FAVORITE_LIST);
        optionalUserProfile.get().getFavoriteRecipes().remove(recipeId);
        update(optionalUserProfile.get());
        return true;
    }


    public Boolean removeFavoriteUserRecipe(String recipeId) {
        List<UserProfile> userProfileList = findAll();
        if(userProfileList.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        userProfileList.forEach(userProfile -> {
            userProfile.getFavoriteRecipes().remove(recipeId);
            update(userProfile);
        });
        return true;
    }

    public Boolean checkUserFavoriteFoodsThenMail(FavoriteCategoryAlerterResponseDto dto) {
        Set<UserProfileMailRequestDto> mailedUserProfileList = new HashSet<>();
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        if(userProfileList.isEmpty())
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        userProfileList.forEach(userProfile -> {
            userProfile.getFavoriteRecipes().forEach(favoriteRecipe -> {
               if(dto.getRecipeIds().contains(favoriteRecipe)){
                   mailedUserProfileList.add(IUserProfileMapper.INSTANCE.fromUserProfileToUserProfileMailRequestDto(userProfile));
                   //Mail atılacak ilgili kişiler bir set e toplandı
               }
            });
        });
        FavoriteRecipeAddedNotificationModel model =
                IUserProfileMapper.INSTANCE.fromFavoriteCategoryAlerterToFavoriteRecipeAddedNotificationModel(dto);
        model.setUserProfileMailRequestDtos(mailedUserProfileList);
        favoriteRecipeAddedNotificationProducer.sendNotificationFavoriteRecipeAdded(model);
        return true;
    }
}
