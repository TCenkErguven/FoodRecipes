package com.foody.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@Document
public class Category extends Base implements Serializable {
    @Id
    private String id;
    @NotBlank
    private String categoryName;
}
