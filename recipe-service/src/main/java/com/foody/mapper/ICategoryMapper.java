package com.foody.mapper;

import com.foody.dto.request.SaveCategoryRequestDto;
import com.foody.dto.request.UpdateCategoryRequestDto;
import com.foody.dto.response.SaveCategoryResponseDto;
import com.foody.dto.response.UpdateCategoryResponseDto;
import com.foody.repository.entity.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {
    ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);

    Category fromSaveCategoryRequestDtoToCategory(final SaveCategoryRequestDto dto);
    SaveCategoryResponseDto fromCategoryToSaveCategoryResponseDto(final Category category);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category fromUpdateCategoryRequestDtoToCategory(UpdateCategoryRequestDto dto,@MappingTarget Category category);
    UpdateCategoryResponseDto fromCategoryToUpdateCategoryResponseDto(Category category);
}
