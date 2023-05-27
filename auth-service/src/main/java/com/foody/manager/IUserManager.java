package com.foody.manager;

import com.foody.dto.response.RegisterUserProfileResponseDto;
import com.foody.dto.response.ForgotPasswordUserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "http://localhost:9080/api/v1/user", name = "auth-userprofile",decode404 = true)
public interface IUserManager {
    @PostMapping("/register-userprofile")
    public ResponseEntity<Boolean> registerUserProfile(@RequestBody RegisterUserProfileResponseDto dto);

    @PutMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatusUserProfile(@PathVariable Long authId);

    @PutMapping("/forgot-password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody ForgotPasswordUserProfileResponseDto dto);

}
