package org.questgame.webquestgamespring.service.user;

import lombok.RequiredArgsConstructor;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.UserNotFoundException;
import org.questgame.webquestgamespring.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User " + username + " is not found")
		);
		return User.builder()
				.username(username)
				.password(entity.getPassword())
				.authorities(entity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList())
				.build();
	}
}
