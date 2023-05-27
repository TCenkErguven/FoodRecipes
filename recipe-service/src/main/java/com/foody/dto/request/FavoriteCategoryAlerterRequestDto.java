package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class FavoriteCategoryAlerterRequestDto {
    private String foodName;
    private Set<String> categoryNames;
    private Set<String> recipeIds;
}
