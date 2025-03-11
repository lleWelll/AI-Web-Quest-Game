package org.questgame.webquestgamespring.controller;

import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.StoryEntity;
import org.questgame.webquestgamespring.util.StorySerializer;
import org.questgame.webquestgamespring.service.StoryMongoService;
import org.questgame.webquestgamespring.util.Deserializer;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/test")
public class TestController {
	private final StoryMongoService service;

	@PostMapping("/save")
	public void save() {
		Story story = StorySerializer.getStoryFromFile("/Users/llwll/Downloads/story-2025-03-11T13_58_29.353585.ser");
		StoryEntity e = new StoryEntity(story);
		service.saveStory(e);
	}

	@GetMapping("/get/{id}")
	public String getById(@PathVariable String id) {
		Story s = new Story(Deserializer.deserialize(service.getById(id).getElements().getData()));
		return s.getSTORY_ELEMENTS().toString();

	}

	public TestController(StoryMongoService service) {
		this.service = service;
	}
}
