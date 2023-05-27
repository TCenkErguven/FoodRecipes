package com.foody.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
@Entity
public class Address extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
}
