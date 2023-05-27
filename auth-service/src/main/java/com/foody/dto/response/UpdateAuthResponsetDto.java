package com.foody.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateAuthResponsetDto {
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String street;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
}
