package org.questgame.webquestgamespring.mapper;

import org.mapstruct.Mapper;
import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.model.dto.user.UserResponseDto;
import org.questgame.webquestgamespring.model.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserEntity toUserEntity(UserRegistrationForm registrationForm);

	UserResponseDto toUserResponse(UserEntity entity);

}
