package org.questgame.webquestgamespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthorizationController {

	@GetMapping("/registration")
	public String getRegistrationPage() {
		return "registration";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

}
