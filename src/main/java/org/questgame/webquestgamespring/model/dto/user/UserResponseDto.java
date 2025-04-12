package org.questgame.webquestgamespring.model.dto.user;

import lombok.Data;
import org.questgame.webquestgamespring.model.dto.role.RoleNameDto;

import java.util.List;

@Data
public class UserResponseDto {

	private Long id;

	private String username;

	private List<RoleNameDto> roles;

}
