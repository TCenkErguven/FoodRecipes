package com.foody.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RemovePointFromRecipeRequestDto {
    private String pointId;
    private String recipeId;
}
