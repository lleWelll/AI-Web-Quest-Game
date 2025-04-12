package org.questgame.webquestgamespring.controller;

import lombok.RequiredArgsConstructor;
import org.questgame.webquestgamespring.model.dto.role.RoleNameDto;
import org.questgame.webquestgamespring.model.dto.user.UserLoginDto;
import org.questgame.webquestgamespring.model.dto.user.UserRegistrationForm;
import org.questgame.webquestgamespring.model.exceptions.RegistrationException;
import org.questgame.webquestgamespring.service.user.UserDaoService;
import org.questgame.webquestgamespring.service.user.UserRegistrationService;
import org.questgame.webquestgamespring.util.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginAndRegistrationController {

	private final UserRegistrationService userRegistrationService;

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
		userRegistrationService.processRegistration(userRegistrationForm);
		return "forward:/login";
	}
}
