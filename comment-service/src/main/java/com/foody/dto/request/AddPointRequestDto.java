package com.foody.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AddPointRequestDto {
    private String recipeId;
    @Max(5)
    @Min(0)
    private Double point;
}
