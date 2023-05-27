package com.foody.manager;

import com.foody.dto.request.UpdateAuthRequestDto;
import com.foody.dto.response.ChangePasswordAuthResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "http://localhost:9090/api/v1/auth", name = "userprofile-auth",decode404 = true)
public interface IAuthManager {
    @PutMapping("/change-password")
    public ResponseEntity<Boolean> changePasswordAuth(@RequestBody ChangePasswordAuthResponseDto dto);
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateAuthRequestDto dto);
    @DeleteMapping("/delete/{authId}")
    public ResponseEntity<Boolean> deleteAuth(@PathVariable Long authId);
    @PutMapping("/inactivate/{authId}")
    public ResponseEntity<Boolean> inactivateAuth(@PathVariable Long authId);


}
