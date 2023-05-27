package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateCommentRequestDto {
    @NotBlank
    private String commentId;
    @NotBlank
    private String comment;
    @NotBlank
    private String recipeId;
}
