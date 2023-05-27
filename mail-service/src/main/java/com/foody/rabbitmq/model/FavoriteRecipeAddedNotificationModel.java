package com.foody.rabbitmq.model;

import com.foody.dto.request.UserProfileMailRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class FavoriteRecipeAddedNotificationModel implements Serializable {
    private Set<UserProfileMailRequestDto> userProfileMailRequestDtos;
    private Set<String> categoryNames;
    private String foodName;
}
