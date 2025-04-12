package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.UserMapper;
import org.questgame.webquestgamespring.model.dto.user.UserLoginDto;
import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.model.exceptions.RegistrationException;
import org.questgame.webquestgamespring.util.Validator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationService {

	private final UserDaoService userDaoService;

	private final UserMapper mapper;

	public void processRegistration(UserRegistrationForm userRegistrationForm) {
		log.info("Started registration process of new User");
		validateCredentials(userRegistrationForm);

		UserLoginDto loginDto = mapper.toUserLogin(userRegistrationForm);
		userDaoService.registerNewUser(loginDto);
		log.info("User registration successful");
	}

	private void validateCredentials(UserRegistrationForm userRegistrationForm) {
		if (!Validator.confirmPassword(userRegistrationForm.getPassword(), userRegistrationForm.getPasswordConfirmation())) {
			throw new RegistrationException("Passwords does not match");
		}
		if (!Validator.isValidPassword(userRegistrationForm.getPassword())) {
			throw new RegistrationException("Password should be more than 8 characters and " +
					"doesn't contain '|', '\\', '/', '-', '+', '=', '.', ',', '^', '`', '~', ' '");
		}
		if (userDaoService.isUserExists(userRegistrationForm.getUsername())) {
			throw new RegistrationException("User with this name already exists");
		}
	}

}
