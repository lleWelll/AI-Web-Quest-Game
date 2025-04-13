package org.questgame.webquestgamespring.repository;

import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

	List<StoryEntity> findAllByUserId(Long userId);

	List<StoryEntity> findAllByUserUsername(String username);

}
