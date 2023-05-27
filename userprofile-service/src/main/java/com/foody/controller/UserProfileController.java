package com.foody.controller;

import com.foody.dto.request.ChangePasswordRequestDto;
import com.foody.dto.request.UpdateUserProfileRequestDto;
import com.foody.dto.response.FavoriteCategoryAlerterResponseDto;
import com.foody.dto.response.ForgotPasswordUserProfileResponseDto;
import com.foody.dto.response.RegisterUserProfileResponseDto;
import com.foody.dto.response.UserProfileResponseDto;
import com.foody.repository.entity.UserProfile;
import com.foody.service.UserProfileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Hidden
    @PostMapping("/register-userprofile")
    public ResponseEntity<Boolean> registerUserProfile(@RequestBody RegisterUserProfileResponseDto dto){
        return ResponseEntity.ok(userProfileService.registerUserProfile(dto));
    }
    @Hidden
    @PutMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatusUserProfile(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatusUserProfile(authId));
    }
    @Hidden
    @PutMapping("/forgot-password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody ForgotPasswordUserProfileResponseDto dto){
        return ResponseEntity.ok(userProfileService.forgotPassword(dto));
    }
    @PutMapping("/change-password/{token}")
    public ResponseEntity<Boolean> changePassword(@PathVariable String token,@RequestBody ChangePasswordRequestDto dto){
        if(!dto.getNewPassword().equals(dto.getReNewPassword()))
            throw new RuntimeException("INCORRECT PASSWORD");
        return ResponseEntity.ok(userProfileService.changePassword(token,dto));
    }
    @PutMapping("/update/{token}")
    public ResponseEntity<UserProfile> update(@PathVariable String token,@RequestBody @Valid UpdateUserProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(token,dto));
    }
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<Boolean> delete(@PathVariable String token){
        return ResponseEntity.ok(userProfileService.deleteAccount(token));
    }
    @PutMapping("/inactivate/{token}")
    public ResponseEntity<Boolean> inactivate(@PathVariable String token){
        return ResponseEntity.ok(userProfileService.inactivate(token));
    }

    @Hidden
    @GetMapping("/get-userprofile-dto/{authId}")
    public ResponseEntity<UserProfileResponseDto> getUserProfileDto(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.getUserProfileDto(authId));
    }

    @PutMapping("/add-favorite-recipe/{token}/{recipeId}")
    public ResponseEntity<Boolean> addFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.addFavoriteRecipe(token,recipeId));
    }

    @DeleteMapping ("/drop-favorite-recipe/{token}/{recipeId}")
    public ResponseEntity<Boolean> dropFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.dropFavoriteRecipe(token,recipeId));
    }

    @Hidden
    @PutMapping("/remove-favorite-user-recipe/{recipeId}")
    public ResponseEntity<Boolean> removeFavoriteUserRecipe(@PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.removeFavoriteUserRecipe(recipeId));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @Hidden
    @PostMapping("/check-user-favorite-foods-then-mail")
    public ResponseEntity<Boolean> checkUserFavoriteFoodsThenMail(@RequestBody FavoriteCategoryAlerterResponseDto dto){
        return ResponseEntity.ok(userProfileService.checkUserFavoriteFoodsThenMail(dto));
    }

}
