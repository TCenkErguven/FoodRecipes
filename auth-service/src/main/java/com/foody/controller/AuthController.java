package com.foody.controller;

import com.foody.dto.request.ActivateStatusRequestDto;
import com.foody.dto.request.ForgotPasswordRequestDto;
import com.foody.dto.request.LoginRequestDto;
import com.foody.dto.request.RegisterAuthRequestDto;
import com.foody.dto.response.ChangePasswordAuthResponseDto;
import com.foody.dto.response.RegisterAuthResponseDto;
import com.foody.dto.response.UpdateAuthResponsetDto;
import com.foody.exception.AuthManagerException;
import com.foody.exception.ErrorType;
import com.foody.repository.entity.Auth;
import com.foody.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.foody.constants.ApiUrls.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterAuthResponseDto> registerUser(@RequestBody @Valid RegisterAuthRequestDto dto){
        if(!dto.getPassword().equals(dto.getRePassword())){
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        return ResponseEntity.ok(authService.registerUser(dto));
    }
    @PutMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody @Valid ActivateStatusRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDto dto){
        return ResponseEntity.ok(authService.forgotPassword(dto));
    }
    @Hidden
    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<Boolean> changePasswordAuth(@RequestBody ChangePasswordAuthResponseDto dto){
        return ResponseEntity.ok(authService.changePassword(dto));
    }
    @Hidden
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateAuthResponsetDto dto){
        return ResponseEntity.ok(authService.update(dto));
    }
    @Hidden
    @DeleteMapping(DELETE + "/{authId}")
    public ResponseEntity<Boolean> deleteAuth(@PathVariable Long authId){
        return ResponseEntity.ok(authService.deleteAuth(authId));
    }
    @Hidden
    @PutMapping(INACTIVE + "/{authId}")
    public ResponseEntity<Boolean> inactivateAuth(@PathVariable Long authId){
        return ResponseEntity.ok(authService.inactivateAuth(authId));
    }


    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }

}
