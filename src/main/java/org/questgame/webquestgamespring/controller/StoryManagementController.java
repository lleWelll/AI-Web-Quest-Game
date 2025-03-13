package org.questgame.webquestgamespring.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.service.StoryManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
@Slf4j
public class StoryManagementController {

	private final StoryManagementService managementService;


	@PostMapping("/saveFile")
	public void saveFromFile(HttpServletRequest req) throws ServletException, IOException {
		managementService.saveFromFile(req);
	}

	@PostMapping("/saveCurrent")
	public void saveCurrentStory(HttpSession session) {
		managementService.saveCurrentStory(session);
	}

	@GetMapping("/get/{id}")
	public String getById(@PathVariable String id) {
		return managementService.getById(id);
	}
}
