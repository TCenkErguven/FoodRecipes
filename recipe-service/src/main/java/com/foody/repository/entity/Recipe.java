package com.foody.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@Document
public class Recipe extends Base implements Serializable {
    @Id
    private String id;
    @NotBlank
    private String foodName;
    @NotBlank
    private String preparationTime;
    @NotBlank
    private String cookingTime;
    @NotBlank
    private String cookingSteps;
    @NotNull
    private List<String> types;
    @NotNull
    private List<String> categoryIds;
    @NotNull
    private List<String> images;
    @NotNull
    private List<Ingredient> ingredientList;
    @NotNull
    private Nutritional nutritional;
    private List<String> commentIds = new ArrayList<>();
    private List<String> pointIds = new ArrayList<>();
}
