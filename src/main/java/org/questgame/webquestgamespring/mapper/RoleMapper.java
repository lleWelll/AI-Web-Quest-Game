package org.questgame.webquestgamespring.mapper;

import org.mapstruct.Mapper;
import org.questgame.webquestgamespring.model.dto.role.RoleDto;
import org.questgame.webquestgamespring.model.dto.role.RoleNameDto;
import org.questgame.webquestgamespring.model.entity.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	RoleEntity toEntity(RoleNameDto roleNameDto);

	RoleDto toRoleDto(RoleEntity roleEntity);

	RoleNameDto toRoleNameDto(RoleEntity entity);
}
