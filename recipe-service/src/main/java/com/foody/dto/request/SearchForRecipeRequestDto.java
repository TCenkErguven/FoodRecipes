package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SearchForRecipeRequestDto {
    private List<String> categoryIds;
    private String foodName;
    private List<String> ingredientName;
}
