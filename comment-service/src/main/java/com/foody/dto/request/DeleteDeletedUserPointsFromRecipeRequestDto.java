package com.foody.dto.request;

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
public class DeleteDeletedUserPointsFromRecipeRequestDto {
    Set<String> deletedPointRecipeIds;
    List<String> deletedPointIds;
}
