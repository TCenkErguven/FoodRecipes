package com.foody.mapper;

import com.foody.dto.request.SaveRecipeRequestDto;
import com.foody.dto.request.UpdateRecipeRequestDto;
import com.foody.dto.response.SaveRecipeResponseDto;
import com.foody.dto.response.UpdateRecipeResponseDto;
import com.foody.repository.entity.Recipe;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRecipeMapper {
    IRecipeMapper INSTANCE = Mappers.getMapper(IRecipeMapper.class);

    Recipe fromSaveRecipeRequestDtoToRecipe(final SaveRecipeRequestDto dto);
    SaveRecipeResponseDto fromRecipeToSaveRecipeResponseDto(final Recipe recipe);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe fromUpdateRecipeRequestDtoToRecipe(UpdateRecipeRequestDto dto,@MappingTarget Recipe recipe);
    UpdateRecipeResponseDto fromRecipeToUpdateRecipeResponseDto(final Recipe recipe);


}
