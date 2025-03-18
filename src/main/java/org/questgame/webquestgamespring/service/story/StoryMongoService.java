package org.questgame.webquestgamespring.service.story;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.exceptions.StoryNotFoundException;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryMongoService {

	private final StoryRepository storyRepository;

	public void saveStory(StoryEntity story) {
		storyRepository.save(story);
		log.info("story saved in database");
	}

	public StoryEntity getStoryById(String id) {
		return storyRepository.findById(id).orElseThrow(
				() -> {
					log.info("Story with {} not found", id);
					return new StoryNotFoundException("Story with id " + id + " not found");
				}
		);
	}

	public void deleteStoryById(@NonNull String id) {
		storyRepository.deleteById(id);
	}

	public void updateStory(@NonNull String id, Consumer<StoryEntity> updateFunction) {
		StoryEntity entity = getStoryById(id);
		updateFunction.accept(entity);
		saveStory(entity);
	}
}
