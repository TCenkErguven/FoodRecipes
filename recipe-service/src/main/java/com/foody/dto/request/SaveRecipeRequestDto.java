package com.foody.dto.request;

import com.foody.repository.entity.Ingredient;
import com.foody.repository.entity.Nutritional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SaveRecipeRequestDto {
    @NotBlank (message = "Enter a valid foodName")
    private String foodName;
    @NotBlank (message = "Enter a valid preparationTime")
    private String preparationTime;
    @NotBlank (message = "Enter a valid cookingTime")
    private String cookingTime;
    @NotBlank (message = "Enter a valid cooking steps")
    private String cookingSteps;
    @NotNull (message = "Enter a valid cooking types")
    private List<String> types;
    @NotNull (message = "Enter a valid cooking categorys")
    private List<String> categoryIds;
    @NotNull (message = "Enter a valid cooking images")
    private List<String> images;
    @NotNull (message = "Enter a valid cooking ingredients")
    private List<Ingredient> ingredientList;
    @NotNull (message = "Enter a valid cooking nutritional")
    private Nutritional nutritional;
}
