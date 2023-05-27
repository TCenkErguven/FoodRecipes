package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DeleteCommentToRecipeRequestDto {
    private String recipeId;
    private String commentId;
}
