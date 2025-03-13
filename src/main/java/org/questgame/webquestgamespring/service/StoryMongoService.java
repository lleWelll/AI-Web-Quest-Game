package org.questgame.webquestgamespring.service;

import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.exceptions.StoryNotFoundException;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StoryMongoService {
	private final StoryRepository storyRepository;

	public void saveStory(StoryEntity story) {
		storyRepository.save(story);
		log.info("story saved in database");
	}

	public StoryEntity getById(String id) {
		return storyRepository.findById(id).orElseThrow(
				() -> new StoryNotFoundException()
		);
	}

	public StoryMongoService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}
}
