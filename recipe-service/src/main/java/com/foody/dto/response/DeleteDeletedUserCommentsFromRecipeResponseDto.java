package com.foody.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DeleteDeletedUserCommentsFromRecipeResponseDto {
    private List<String> deletedCommentIds;
    private Set<String> deletedCommentRecipeIds;
}
