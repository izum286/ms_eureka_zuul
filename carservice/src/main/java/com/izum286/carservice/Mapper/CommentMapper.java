package com.izum286.carservice.Mapper;


import com.izum286.carservice.model.entity.CommentEntity;
import com.izum286.carservice.model.entity.UserEntity;
import com.izum286.search.Models.AddCommentDTO;
import com.izum286.search.Models.FullCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "post_date", source = "comment.postDateTime")
    @Mapping(target = "first_name", source = "comment.lastName", defaultValue = "none")
    @Mapping(target = "second_name", source = "comment.firstName", defaultValue = "none")
    @Mapping(target = "photo", source = "photo", defaultValue = "https://a.d-cd.net/4e0c9b9s-1920.jpg")
    FullCommentDTO map(CommentEntity comment);

    @Mapping(target = "postDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "ownerEmail", source = "postOwner.email")
    CommentEntity map(AddCommentDTO comment, String serialNumber, UserEntity postOwner);


}
