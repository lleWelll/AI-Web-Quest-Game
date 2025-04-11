package org.questgame.webquestgamespring.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.service.InitializationService;
import org.questgame.webquestgamespring.service.StoryFileService;
import org.questgame.webquestgamespring.service.StoryManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping()
@Slf4j
@RequiredArgsConstructor
public class GameController {

	private final StoryManagementService storyManagementService;
	private final StoryFileService fileService;
	private final InitializationService initService;

	@GetMapping("/")
	public String welcome() {
		return "welcome-page";
	}

	@PostMapping("/generate")
	public String generateStory(@RequestParam String userPrompt, HttpSession session) {
		return  initService.generateStory(userPrompt, session);
	}

	@PostMapping("/init")
	public String init(Model model, HttpSession session) {
		return initService.init(model, session);
	}

	@GetMapping("/story")
	public String move(@RequestParam Integer index, Model model, HttpSession session) {
		return storyManagementService.move(index, model, session);
	}

	@GetMapping("/finish")
	public String finish(Model model, HttpSession session) {
		return storyManagementService.finish(model, session);
	}

	@GetMapping("/restart")
	public String restartGame(HttpSession session) {
		return storyManagementService.restartGame(session);
	}

	@GetMapping("/restartStory")
	public String restartCurrentStory(Model model, HttpSession session) {
		return storyManagementService.restartCurrentStory(model, session);
	}

	@GetMapping("/download")
	public void downloadStory(HttpSession session, HttpServletResponse resp) throws IOException {
		fileService.downloadStory(session, resp);
	}

	@PostMapping("/upload")
	public String uploadStory(HttpServletRequest req, HttpSession session) throws ServletException, IOException {
		return fileService.uploadStoryAndRedirectIndex(req, session);
	}
}
