package com.foody.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Nutritional implements Serializable {
    private String nutrionalName;
    @Min(0)
    private Double calories;
    @Min(0)
    private Double protein;
    @Min(0)
    private Double carbohydrate;
    @Min(0)
    private Double fat;
}
