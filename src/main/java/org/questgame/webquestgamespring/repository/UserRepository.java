package org.questgame.webquestgamespring.repository;

import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
