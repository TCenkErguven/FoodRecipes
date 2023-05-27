package com.foody.mapper;

import com.foody.dto.request.AddCommentRequestDto;
import com.foody.dto.request.DeleteCommentRequestDto;
import com.foody.dto.request.DeleteCommentToRecipeRequestDto;
import com.foody.dto.request.UpdateCommentRequestDto;
import com.foody.dto.response.AddCommentResponseDto;
import com.foody.dto.response.UpdateCommentResponseDto;
import com.foody.repository.entity.Comment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    Comment fromAddCommentRequestDtoToComment(AddCommentRequestDto dto);
    DeleteCommentToRecipeRequestDto fromDeleteCommentRequestDtoToDeleteCommentResponseDto(final DeleteCommentRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment fromUpdateCommentRequestDtoToComment(UpdateCommentRequestDto dto, @MappingTarget Comment comment);
    UpdateCommentResponseDto fromCommentToUpdateCommentResponseDto(Comment comment);

}
