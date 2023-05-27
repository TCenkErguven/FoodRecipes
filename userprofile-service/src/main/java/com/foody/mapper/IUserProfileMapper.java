package com.foody.mapper;

import com.foody.dto.request.RemoveAllUserCommentsAndPointsRequestDto;
import com.foody.dto.request.UpdateAuthRequestDto;
import com.foody.dto.request.UpdateUserProfileRequestDto;
import com.foody.dto.request.UserProfileMailRequestDto;
import com.foody.dto.response.ChangePasswordAuthResponseDto;
import com.foody.dto.response.FavoriteCategoryAlerterResponseDto;
import com.foody.dto.response.ForgotPasswordUserProfileResponseDto;
import com.foody.dto.response.RegisterUserProfileResponseDto;
import com.foody.rabbitmq.model.FavoriteRecipeAddedNotificationModel;
import com.foody.rabbitmq.producer.FavoriteRecipeAddedNotificationProducer;
import com.foody.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.control.MappingControl;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile fromRegisterUserProfileRequestDto(final RegisterUserProfileResponseDto dto);
    ForgotPasswordUserProfileResponseDto fromForgotPasswordUserProfileResponseDtoToUserProfile(final ForgotPasswordUserProfileResponseDto dto);
    ChangePasswordAuthResponseDto fromUserProfileToChangePasswordAuthResponseDto(final UserProfile userProfile);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile fromUpdateUserProfileRequestDtoToUserProfile(UpdateUserProfileRequestDto dto, @MappingTarget UserProfile userProfile);
    UpdateAuthRequestDto fromUserProfileToUpdateAuthRequestDto(final UserProfile userProfile);
    RemoveAllUserCommentsAndPointsRequestDto userProfileToremoveCommentAndPointRequestDto(final UserProfile userProfile);
    UserProfileMailRequestDto fromUserProfileToUserProfileMailRequestDto(final UserProfile userProfile);
    FavoriteRecipeAddedNotificationModel fromFavoriteCategoryAlerterToFavoriteRecipeAddedNotificationModel(final FavoriteCategoryAlerterResponseDto dto);
}
