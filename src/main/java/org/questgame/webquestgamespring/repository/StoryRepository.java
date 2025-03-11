package org.questgame.webquestgamespring.repository;

import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.StoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends MongoRepository<StoryEntity, String> {

}
