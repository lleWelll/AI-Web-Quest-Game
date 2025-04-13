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
		log.info("Getting UserEntity with id {}", id);
		return findById(id);
	}

	@Override
	public UserEntity getEntityByName(String name) {
		log.info("Getting UserEntity with name {}", name);
		return findByUsername(name);
	}

	@Override
	public UserResponseDto getById(Long id) {
		log.info("Getting User with id {}", id);
		return mapper.toUserResponse(findById(id));
	}

	@Override
	public UserResponseDto getByName(String username) {
		log.info("Returning user with username {}", username);
		return mapper.toUserResponse(findByUsername(username));
	}

	private UserEntity findById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User with id " + id + " is not found")
		);
	}

	private UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User with username " + username + " is not found")
		);
	}
}