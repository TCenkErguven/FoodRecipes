package com.foody.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
@Document
public class Point extends Base{
    @Id
    private String id;;
    private Double point;
    private String userProfileId;
    private String recipeId;

}
