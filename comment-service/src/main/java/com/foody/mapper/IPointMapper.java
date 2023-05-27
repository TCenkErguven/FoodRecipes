package com.foody.mapper;

import com.foody.dto.request.AddPointIdToRecipeRequestDto;
import com.foody.dto.request.AddPointRequestDto;
import com.foody.dto.response.RemovePointFromRecipeRequestDto;
import com.foody.dto.response.AddPointResponseDto;
import com.foody.repository.entity.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPointMapper {
    IPointMapper INSTANCE = Mappers.getMapper(IPointMapper.class);

    Point fromAddPointRequestDtoToPoint(final AddPointRequestDto dto);
    @Mapping(source = "id", target = "pointId")
    AddPointIdToRecipeRequestDto pointToAddPointIdToRecipeRequestDto(final Point point);
    AddPointResponseDto pointToAddPointResponseDto(final Point point);
    @Mapping(source = "id", target = "pointId")
    RemovePointFromRecipeRequestDto pointToRemovePointFromRecipe(final Point point);

}
