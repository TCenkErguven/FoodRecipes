package com.foody.mapper;

import com.foody.dto.request.RegisterAuthRequestDto;
import com.foody.dto.response.ForgotPasswordUserProfileResponseDto;
import com.foody.dto.response.RegisterUserProfileResponseDto;
import com.foody.dto.response.RegisterAuthResponseDto;
import com.foody.dto.response.UpdateAuthResponsetDto;
import com.foody.rabbitmq.model.ForgotPasswordMailModel;
import com.foody.rabbitmq.model.RegisterMailModel;
import com.foody.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromRegisterRequestDtoToAuth(final RegisterAuthRequestDto dto);
    RegisterAuthResponseDto fromAuthToRegisterResponseDto(final Auth auth);
    @Mapping(source = "id",target = "authId")
    RegisterUserProfileResponseDto fromAuthToRegisterUserProfileResponseDto(final Auth auth);
    @Mapping(source = "id",target = "authId")
    ForgotPasswordUserProfileResponseDto fromAuthToForgotPasswordUserProfileResponseDto(final Auth auth);
    RegisterMailModel fromAuthToRegisterMailModel(final Auth auth);
    ForgotPasswordMailModel fromAuthToForgotPasswordMailModel(final Auth auth);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auth fromUpdateAuthResponseDtoToAuth(UpdateAuthResponsetDto dto,@MappingTarget Auth auth);

}
