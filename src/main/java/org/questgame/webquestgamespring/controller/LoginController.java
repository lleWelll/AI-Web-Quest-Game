package org.questgame.webquestgamespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

	@GetMapping("/registration")
	public String getRegistrationPage() {
		return "registration";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

}
