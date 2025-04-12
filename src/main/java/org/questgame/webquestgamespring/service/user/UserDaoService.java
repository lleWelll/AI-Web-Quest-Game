package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.UserMapper;
import org.questgame.webquestgamespring.model.dto.user.UserLoginDto;
import org.questgame.webquestgamespring.model.dto.user.UserResponseDto;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.questgame.webquestgamespring.service.role.RoleDaoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDaoService {

	private final UserRepository userRepository;

	private final UserMapper mapper;

	private final PasswordEncoder passwordEncoder;

	private final RoleDaoService roleService;

	public UserResponseDto getById(Long id) {
		UserEntity entity = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User with id " + id + " is not found")
		);
		log.info("Returning user with id {}", id);
		return mapper.toUserResponse(entity);
	}

	public UserResponseDto getByUsername(String username) {
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User with username " + username + " is not found")
		);
		log.info("Returning user with username {}", username);
		return mapper.toUserResponse(entity);
	}

	@Transactional
	public void registerNewUser(UserLoginDto loginDto) {
		UserEntity entity = mapper.toUserEntity(loginDto);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setRoles(List.of(roleService.getByName("ROLE_USER")));
		userRepository.save(entity);
		log.info("User {} saved with id: {}, roles: {}", entity.getUsername(), entity.getId(), entity.getRoles());
	}

	public boolean isUserExists(String username) {
		log.info("Checking existing of user {}", username);
		try {
			getByUsername(username);
		} catch (UserNotFoundException ignore) {
			return false;
		}
		return true;
	}
}
