package org.questgame.webquestgamespring.controller;

import org.questgame.webquestgamespring.mapper.StoryMapper;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.storyElements.Element;
import org.questgame.webquestgamespring.util.StorySerializer;
import org.questgame.webquestgamespring.service.StoryMongoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/test")
public class TestController {
	private final StoryMongoService service;

	private final StoryMapper mapper;

	@PostMapping("/save")
	public void save() {
		Story story = StorySerializer.getStoryFromFile("/Users/llwll/Downloads/story-2025-03-11T13_58_29.353585.ser");
		StoryEntity e = mapper.toEntity(story);
		service.saveStory(e);
	}

	@GetMapping("/get/{id}")
	public String getById(@PathVariable String id) {
		Story s = mapper.toStory(service.getById(id));
		return s.getSTORY_ELEMENTS().toString();
	}

	public TestController(StoryMongoService service, StoryMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
}
