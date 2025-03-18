package org.questgame.webquestgamespring.service.story;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.service.StoryFileService;
import org.questgame.webquestgamespring.service.user.UserService;
import org.questgame.webquestgamespring.util.StorySerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryManagementService {

	private final StoryMongoService storyService;

	private final UserService userService;

	private final StoryFileService fileService;

	private final StoryMapper mapper;

	public void saveFromFile(HttpServletRequest req) throws ServletException, IOException {
		log.info("saving story from file");
		Story story = StorySerializer.getStoryFromFile(fileService.uploadFile(req));
		StoryEntity e = mapper.toEntity(story);
		storyService.saveStory(e);
	}

	public void saveCurrentStory(@NonNull HttpSession session) {
		log.info("saving story from session to database");
		StoryEntity entity = mapper.toEntity((Story) session.getAttribute("story"));
		storyService.saveStory(entity);
	}

	public String getById(@NonNull String id) {
		Story s = mapper.toStory(storyService.getStoryById(id));
		return s.getSTORY_ELEMENTS().toString();
	}
}
