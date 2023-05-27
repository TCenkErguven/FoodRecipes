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
@SuperBuilder
@Data
@NoArgsConstructor
@Document
public class Comment extends Base{
    @Id
    private String id;
    private String userProfileId;
    private String username;
    @NotBlank
    private String comment;
    @Builder.Default
    private Long commentDate = System.currentTimeMillis();
    private String recipeId;
}
