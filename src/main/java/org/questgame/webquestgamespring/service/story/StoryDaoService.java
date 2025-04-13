package org.questgame.webquestgamespring.service.story;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.dto.story.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.exceptions.StoryNotFoundException;
import org.questgame.webquestgamespring.repository.StoryRepository;
import org.questgame.webquestgamespring.service.interfaces.DaoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryDaoService implements DaoService<StoryEntity, Story> {

	private final StoryRepository storyRepository;

	private final StoryMapper mapper;

	@Override
	public StoryEntity getEntityById(Long id) {
		log.info("Getting story with id {}", id);
		return findById(id);
	}

	@Override
	public StoryEntity getEntityByName(String name) {
		throw new UnsupportedOperationException("Unsupported method");
	}

	public List<StoryEntity> getStoryEntitiesByUserId(Long id) {
		List<StoryEntity> allByUserId = storyRepository.findAllByUserId(id);
		if (allByUserId == null) throw new StoryNotFoundException("User " + id + " doesn't have stories");
		else return allByUserId;
	}

	public List<StoryEntity> getStoryEntitiesByUserUsername(String username) {
		List<StoryEntity> allByUserId = storyRepository.findAllByUserUsername(username);
		if (allByUserId == null) throw new StoryNotFoundException("User " + username + " doesn't have stories");
		else return allByUserId;
	}

	@Override
	public Story getById(Long id) {
		log.info("Getting story with id {}", id);
		return mapper.toStoryDto(findById(id));

	}

	@Override
	public Story getByName(String name) {
		throw new UnsupportedOperationException("Unsupported method");
	}

	public void save(StoryEntity entity) {
		storyRepository.save(entity);
		log.info("Story saved successfully");
	}

	private StoryEntity findById(Long id) {
		return storyRepository.findById(id).orElseThrow(
				() -> new StoryNotFoundException("Story " + id + " is not found")
		);
	}
}
