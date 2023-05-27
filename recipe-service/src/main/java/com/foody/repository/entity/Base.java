package com.foody.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
public class Base implements Serializable {
    private Long createdDate;
    private Long updatedDate;
}
