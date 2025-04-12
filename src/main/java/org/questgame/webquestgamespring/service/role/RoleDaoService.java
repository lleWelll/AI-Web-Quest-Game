package org.questgame.webquestgamespring.service.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.RoleMapper;
import org.questgame.webquestgamespring.model.entity.RoleEntity;
import org.questgame.webquestgamespring.model.exceptions.RoleNotFoundException;
import org.questgame.webquestgamespring.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoleDaoService {

	private final RoleRepository roleRepository;

	public RoleEntity getById(Long id) throws RoleNotFoundException {
		log.info("Getting role with id: {}", id);
		return roleRepository.findById(id).orElseThrow(
				() -> new RoleNotFoundException("Role " + id + " is not found")
		);
	}

	public RoleEntity getByName(String name) throws RoleNotFoundException {
		log.info("Getting role with username: {}", name);
		return roleRepository.findByName(name).orElseThrow(
				() -> new RoleNotFoundException("Role " + name + " is not found")
		);
	}
}
