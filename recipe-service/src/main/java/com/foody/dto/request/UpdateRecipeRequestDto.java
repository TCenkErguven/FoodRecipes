package com.foody.dto.request;

import com.foody.repository.entity.Ingredient;
import com.foody.repository.entity.Nutritional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateRecipeRequestDto {
    private String id;
    private String foodName;
    private String preparationTime;
    private String cookingTime;
    private String cookingSteps;
    private List<String> addTypes = new ArrayList<>();
    private List<String> removeTypes = new ArrayList<>();
    private List<String> addCategoryIds = new ArrayList<>();
    private List<String> removeCategoryIds = new ArrayList<>();
    private List<String> addImages = new ArrayList<>();
    private List<String> removeImages = new ArrayList<>();
    private List<Ingredient> removeIngredients = new ArrayList<>();
    private List<Ingredient> addIngredients = new ArrayList<>();
    private Nutritional nutritional;
}
