package com.foody.repository.entity;

import com.foody.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
@Document
public class UserProfile extends Base{
    @Id
    private String id;
    private Long authId;
    private String name;
    private String surname;
    @Email
    @Indexed(unique = true)
    private String email;
    private String password;
    @Builder.Default
    private EStatus status = EStatus.PENDING;
    @Indexed(unique = true)
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
    @Builder.Default
    private List<String> favoriteRecipes = new ArrayList<>();
}
