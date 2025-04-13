package org.questgame.webquestgamespring.service.story;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.dto.story.Story;
import org.questgame.webquestgamespring.model.dto.story.StoryNameAndDescription;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.service.user.UserDaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorySaveService {

	private final StoryDaoService storyDaoService;

	private final UserDaoService userDaoService;

	private final StoryMapper mapper;

	@Transactional
	public void saveStory(Story story, StoryNameAndDescription nameAndDescription, Principal user) {
		StoryEntity entity = mapper.toStoryEntity(story);
		entity.setName(nameAndDescription.getName());
		entity.setDescription(nameAndDescription.getDescription());
		entity.setUser(userDaoService.getEntityByName(user.getName()));
		storyDaoService.save(entity);
	}

}
