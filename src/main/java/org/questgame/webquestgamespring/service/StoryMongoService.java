package org.questgame.webquestgamespring.service;

import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.StoryEntity;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.springframework.stereotype.Service;

@Service
public class StoryMongoService {
	private final StoryRepository storyRepository;

	public void saveStory(StoryEntity story) {
		storyRepository.save(story);
	}

	public StoryEntity getById(String id) {
		return storyRepository.findById(id).orElseThrow(
				() -> new RuntimeException()
		);
	}

	public StoryMongoService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}
}
