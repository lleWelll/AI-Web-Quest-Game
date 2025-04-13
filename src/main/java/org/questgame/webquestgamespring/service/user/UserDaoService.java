package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.UserMapper;
import org.questgame.webquestgamespring.model.dto.user.UserResponseDto;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.questgame.webquestgamespring.service.interfaces.DaoService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDaoService implements DaoService<UserEntity, UserResponseDto> {

	private final UserRepository userRepository;

	private final UserMapper mapper;

	@Override
	public UserEntity getEntityById(Long id) {
		UserEntity entity = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User with id " + id + " is not found")
		);
		log.info("Returning UserEntity with id {}", id);
		return entity;
	}

	@Override
	public UserEntity getEntityByName(String name) {
		UserEntity entity = userRepository.findByUsername(name).orElseThrow(
				() -> new UserNotFoundException("User with name " + name + " is not found")
		);
		log.info("Returning UserEntity with name {}", name);
		return entity;
	}

	@Override
	public UserResponseDto getById(Long id) {
		UserEntity entity = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User with id " + id + " is not found")
		);
		log.info("Returning user with id {}", id);
		return mapper.toUserResponse(entity);
	}

	@Override
	public UserResponseDto getByName(String username) {
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User with username " + username + " is not found")
		);
		log.info("Returning user with username {}", username);
		return mapper.toUserResponse(entity);
	}
}
