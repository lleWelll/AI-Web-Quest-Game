package org.questgame.webquestgamespring.service.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void saveUser(UserEntity entity) {
		userRepository.save(entity);
		log.info("User saved in db");
	}

	public UserEntity getUserById(@NonNull Long id) {
		UserEntity entity = userRepository.findById(id).orElseThrow(
				() -> {
					log.error("User doesn't exist in database");
					return new UserNotFoundException("User doesn't exist in database");
				}
		);
		log.info("User with id {} returned", id);
		return entity;
	}

	public void deleteUserById(@NonNull Long id) {
		UserEntity entity = getUserById(id);
		userRepository.delete(entity);
		log.info("User with id {} deleted", id);
	}

	public void updateUser(@NonNull Long id, Consumer<UserEntity> updateFunction) {
		UserEntity entity = getUserById(id);
		updateFunction.accept(entity);
		saveUser(entity);
		log.info("User with id {} successfully updated", id);
	}
}
