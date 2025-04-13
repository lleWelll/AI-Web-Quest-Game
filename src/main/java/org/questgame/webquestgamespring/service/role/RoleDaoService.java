package org.questgame.webquestgamespring.service.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.RoleMapper;
import org.questgame.webquestgamespring.model.dto.role.RoleDto;
import org.questgame.webquestgamespring.model.entity.RoleEntity;
import org.questgame.webquestgamespring.model.exceptions.RoleNotFoundException;
import org.questgame.webquestgamespring.repository.RoleRepository;
import org.questgame.webquestgamespring.service.interfaces.DaoService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoleDaoService implements DaoService<RoleEntity, RoleDto> {

	private final RoleRepository roleRepository;

	private final RoleMapper mapper;

	@Override
	public RoleEntity getEntityById(Long id) throws RoleNotFoundException {
		log.info("Getting role with id: {}", id);
		return findById(id);
	}

	@Override
	public RoleEntity getEntityByName(String name) throws RoleNotFoundException {
		log.info("Getting role with username: {}", name);
		return findByName(name);
	}

	@Override
	public RoleDto getById(Long id) {
		log.info("Getting role with id: {}", id);
		return mapper.toRoleDto(findById(id));

	}

	@Override
	public RoleDto getByName(String name) {
		log.info("Getting role with username: {}", name);
		return mapper.toRoleDto(findByName(name));
	}

	private RoleEntity findById(Long id) {
		return roleRepository.findById(id).orElseThrow(
				() -> new RoleNotFoundException("Role " + id + " is not found")
		);
	}

	private RoleEntity findByName(String name) {
		return roleRepository.findByName(name).orElseThrow(
				() -> new RoleNotFoundException("Role " + name + " is not found")
		);
	}
}
