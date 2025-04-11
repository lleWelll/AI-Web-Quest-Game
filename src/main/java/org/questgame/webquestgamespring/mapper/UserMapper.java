package org.questgame.webquestgamespring.mapper;

import org.mapstruct.Mapper;
import org.questgame.webquestgamespring.model.dto.UserLoginDto;
import org.questgame.webquestgamespring.model.dto.UserResponseDto;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto toUserResponseDto(UserEntity entity);

	UserLoginDto toUserLoginDto(UserEntity entity);
}
