package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.UserMapper;
import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.questgame.webquestgamespring.service.interfaces.RegistrationService;
import org.questgame.webquestgamespring.service.role.RoleDaoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationService implements RegistrationService {

	private final UserRepository userRepository;

	private final UserMapper mapper;

	private final PasswordEncoder passwordEncoder;

	private final RoleDaoService roleService;

	@Override
	public void registerNewUser(UserRegistrationForm user) {
		log.info("Started registration process of user {}",user.getUsername());
		validateCredentials(user);
		UserEntity entity = mapper.toUserEntity(user);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setRoles(List.of(roleService.getEntityByName("ROLE_USER")));
		userRepository.save(entity);
		log.info("User {} saved with id: {}, roles: {}", entity.getUsername(), entity.getId(), entity.getRoles());
	}

	@Override
	public boolean isUserExists(String username) {
		log.info("Checking existing of user {}", username);
		return userRepository.findByUsername(username).isPresent();
	}

}
