package com.foody.manager;

import com.foody.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9080/api/v1/user", name = "comment-userProfile" ,decode404 = true)
public interface IUserManager {

    @GetMapping("/get-userprofile-dto/{authId}")
    public ResponseEntity<UserProfileResponseDto> getUserProfileDto(@PathVariable Long authId);


}
