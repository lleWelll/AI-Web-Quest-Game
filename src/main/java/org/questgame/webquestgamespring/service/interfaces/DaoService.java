package org.questgame.webquestgamespring.service.interfaces;

public interface DaoService<Entity, Dto> {

	Entity getEntityById(Long id);

	Entity getEntityByName(String name);

	Dto getById(Long id);

	Dto getByName(String name);
}
