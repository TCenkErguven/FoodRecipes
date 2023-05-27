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
public class UpdateUserProfileRequestDto {
    private String name;
    private String surname;
    @Email
    private String email;
    private String username;
    private String avatar;
    private String street;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
}
