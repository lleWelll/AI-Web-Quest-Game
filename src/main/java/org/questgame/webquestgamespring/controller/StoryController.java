package org.questgame.webquestgamespring.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.questgame.webquestgamespring.model.dto.StoryNameAndDescription;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.service.StoryProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {

	private final StoryProvider provider;

	@GetMapping("/get")
	public StoryEntity getStoryById(@RequestParam Long id) {
		return provider.getStoryEntityById(id);
	}

	@GetMapping("/get")
	public List<StoryEntity> getStoriesByUserId(@RequestParam Long userId) {
		return provider.getStoryEntityByUserId(userId);
	}

	@PostMapping("/save")
	public void saveStory(@RequestBody StoryNameAndDescription story, HttpSession session) {
		provider.saveStory(story, session);
	}


}
