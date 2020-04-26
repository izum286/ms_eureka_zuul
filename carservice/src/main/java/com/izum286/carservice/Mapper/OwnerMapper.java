package com.izum286.carservice.Mapper;


import com.izum286.carservice.model.dto.OwnerDtoResponse;
import com.izum286.carservice.model.entity.OwnerEntity;
import com.izum286.carservice.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CommentMapper.class)
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    @Mapping(target = "firstName", source = "firstName", defaultValue = "none")
    @Mapping(target = "lastName", source = "lastName", defaultValue = "none")
    OwnerDtoResponse map(OwnerEntity entity);

    OwnerEntity map(UserEntity entity);
}
