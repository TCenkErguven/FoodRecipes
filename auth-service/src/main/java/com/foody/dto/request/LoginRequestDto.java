package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank (message = "Enter your username")
    private String username;
    @NotBlank (message = "Enter your password")
    private String password;
}
