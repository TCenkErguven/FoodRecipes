package com.foody.dto.response;

import com.foody.repository.entity.Ingredient;
import com.foody.repository.entity.Nutritional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateRecipeResponseDto {
    private String foodName;
    private String preparationTime;
    private String cookingTime;
    private String cookingSteps;
    private List<String> types;
    private List<String> categoryIds;
    private List<String> images;
    private List<Ingredient> ingredientList;
    private Nutritional nutritional;
}
