package org.questgame.webquestgamespring.controller;

import lombok.RequiredArgsConstructor;
import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.service.interfaces.RegistrationService;
import org.questgame.webquestgamespring.service.user.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginAndRegistrationController {

	private final RegistrationService registrationService;

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout";
	}

	@GetMapping("/registration")
	public String getRegistrationPage(Model model) {
		model.addAttribute("form", new UserRegistrationForm());
		return "registration";
	}

	@PostMapping("/registration")
	public String registerNewUser(@ModelAttribute("form") UserRegistrationForm userRegistrationForm) {
		registrationService.registerNewUser(userRegistrationForm);
		return "forward:/login";
	}
}
