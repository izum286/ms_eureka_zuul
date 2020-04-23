package com.izum286.search.Mapper;

import com.izum286.search.Models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PricePerDayMapper {
    PricePerDayMapper INSTANCE = Mappers.getMapper(PricePerDayMapper.class);

    PricePerDayEntity map(PricePerDayDto dto);

    @Mapping(target = "currency", source = "currency", defaultValue = "ILS")
    PricePerDayDto map(PricePerDayEntity entity);
}
