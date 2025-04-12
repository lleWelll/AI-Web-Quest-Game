package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Getting UserDetails with username: {}", username);
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User with username " + username + " is not found")
		);

		return new User(
				entity.getUsername(),
				entity.getPassword(),
				entity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
		);
	}
}
