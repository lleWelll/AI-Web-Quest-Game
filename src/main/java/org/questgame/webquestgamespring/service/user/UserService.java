package org.questgame.webquestgamespring.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.UserMapper;
import org.questgame.webquestgamespring.model.dto.UserLoginDto;
import org.questgame.webquestgamespring.model.dto.UserResponseDto;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final UserMapper mapper;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UserResponseDto getUserById(Long id) {
		UserEntity entity = getUserEntityById(id);
		log.info("Returning user with id {}", id);
		return mapper.toUserResponseDto(entity);
	}

	@Transactional
	public UserLoginDto getUserLoginDataById(Long id) {
		UserEntity entity = getUserEntityById(id);
		log.info("Returning user with id {}", id);
		return mapper.toUserLoginDto(entity);
	}

	@Transactional
	public UserResponseDto getUserByUsername(String username) {
		UserEntity entity = getUserEntityByUsername(username);
		log.info("Returning user {}", username);
		return mapper.toUserResponseDto(entity);
	}

	@Transactional
	public UserLoginDto getUserLoginDataByUsername(String username) {
		UserEntity entity = getUserEntityByUsername(username);
		log.info("Returning user {}", username);
		return mapper.toUserLoginDto(entity);
	}

	private UserEntity getUserEntityById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User with id " + id + "is not found")
		);
	}

	private UserEntity getUserEntityByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User " + username + "is not found")
		);
	}

	@Transactional
	public void saveUser(String username, String password) {
		UserEntity entity = new UserEntity(username, passwordEncoder.encode(password));
		userRepository.save(entity);
		log.info("User {} saved", username);
	}
}
