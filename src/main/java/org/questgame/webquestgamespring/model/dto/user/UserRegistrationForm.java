package org.questgame.webquestgamespring.model.dto.user;

import lombok.Data;

@Data
public class UserRegistrationForm {

	private String username;

	private String password;

	private String passwordConfirmation;
}
