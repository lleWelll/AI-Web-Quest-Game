package org.questgame.webquestgamespring.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.dto.StoryNameAndDescription;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.StoryNotFoundException;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.questgame.webquestgamespring.util.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryProvider {

	private final StoryRepository storyRepository;

	private final StoryMapper mapper;

	public StoryEntity getStoryEntityById(Long id) {
		checkId(id);
		StoryEntity entity = storyRepository.findById(id).orElseThrow(
				() -> new StoryNotFoundException(String.format("Story with id %d is not found", id))
		);
		log.info("Returning StoryEntity with id {}", id);
		return entity;
	}

	public List<StoryEntity> getStoryEntityByUserId(Long id) {
		checkId(id);
		List<StoryEntity> stories = storyRepository.findAllByUserId(id).orElseThrow(
				() -> new StoryNotFoundException(String.format("User with id %d does not have stories", id))
		);
		log.info("Returning Stories of user {}", id);
		return stories;
	}

	public void saveStory(StoryNameAndDescription story, HttpSession session) {
		Story element = (Story) session.getAttribute("story");
		StoryEntity entity = mapper.storyNameAndDescriptionToEntity(story);
		entity.setUser(new UserEntity());//(UserEntity) session.getAttribute("currentUser")
		entity.setQuest(mapper.mapToBytes(element.getSTORY_ELEMENTS()));
		storyRepository.save(entity);
	}

	public void saveStory(HttpSession session) {
		Story element = (Story) session.getAttribute("story");
		StoryEntity entity = new StoryEntity();
		entity.setName((String) session.getAttribute("storyName"));
		entity.setDescription((String) session.getAttribute("storyDescription"));
		entity.setUser(new UserEntity());//(UserEntity) session.getAttribute("currentUser")
		entity.setQuest(mapper.mapToBytes(element.getSTORY_ELEMENTS()));
		storyRepository.save(entity);
	}

	public void saveStoryWithSpecificUserAndStory(Story story, String name, String description, UserEntity user) {
		StoryEntity entity = mapper.storyToEntity(story);
		entity.setName(name);
		entity.setDescription(description);
		entity.setUser(user);
		storyRepository.save(entity);
		log.info("Story {} saved in DB", name);
	}

	private void checkId(Long id) {
		if (!Validator.isValidId(id)) {
			throw new RuntimeException(String.format("Id %d is not valid, it should be greater than 0", id));
		}
	}
}
