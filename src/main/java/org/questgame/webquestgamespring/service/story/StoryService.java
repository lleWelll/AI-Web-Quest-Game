package org.questgame.webquestgamespring.service.story;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.entity.UserEntity;
import org.questgame.webquestgamespring.model.exceptions.StoryNotFoundException;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryService {

	private final StoryRepository storyRepository;

	private final StoryMapper mapper;

	public void save(String name, String description, HttpSession session) {
		Story story = (Story) session.getAttribute("story");
		UserEntity user = new UserEntity();

		StoryEntity entity = new StoryEntity(name, description, mapper.mapToBytes(story.getSTORY_ELEMENTS()), user);
		storyRepository.save(entity);
		log.info("StoryEntity {} saved", name);
	}

	public StoryEntity getStoryById(Long id) {
		return storyRepository.findById(id).orElseThrow(
				()-> new StoryNotFoundException("Story with id " + id + " is not found")
		);
	}

	public List<StoryEntity> getStoryByUserId(Long userId) {
		return storyRepository.findAllByUserId(userId).orElseThrow(
				() -> new StoryNotFoundException("User " + userId + " doesn't have available stories")
		);
	}

}
