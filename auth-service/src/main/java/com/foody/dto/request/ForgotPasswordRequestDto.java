package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ForgotPasswordRequestDto {
    @Email (message = "Enter a valid email address")
    private String email;
}
