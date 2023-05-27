package com.foody.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AddCommentResponseDto {
    private String username;
    private String comment;
    private Date commentDate;
    private String recipeId;
}
