package com.foody.mapper;

import com.foody.dto.response.UpdateAuthResponsetDto;
import com.foody.repository.entity.Address;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAddressMapper {
    IAddressMapper INSTANCE = Mappers.getMapper(IAddressMapper.class);
    Address fromUpdateAuthResponseDtoToAddressSave(UpdateAuthResponsetDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address fromUpdateAuthResponseDtoToAddress(UpdateAuthResponsetDto dto,@MappingTarget Address address);
}
