package org.questgame.webquestgamespring.service.interfaces;

import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.model.exceptions.RegistrationException;
import org.questgame.webquestgamespring.util.Validator;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {

	@Transactional
	void registerNewUser(UserRegistrationForm user);

	boolean isUserExists(String username);

	default void validateCredentials(UserRegistrationForm user) {
		if (!Validator.confirmPassword(user.getPassword(), user.getPasswordConfirmation())) {
			throw new RegistrationException("Passwords does not match");
		}
		if (!Validator.isValidPassword(user.getPassword())) {
			throw new RegistrationException("Password should be more than 8 characters and " +
					"doesn't contain '|', '\\', '/', '-', '+', '=', '.', ',', '^', '`', '~', ' '");
		}
		if (isUserExists(user.getUsername())) {
			throw new RegistrationException("User with this name already exists");
		}
	}
}
