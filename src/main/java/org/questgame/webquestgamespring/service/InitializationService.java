package org.questgame.webquestgamespring.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.elementHandlers.ElementInitializer;
import org.questgame.webquestgamespring.model.elementHandlers.ElementManager;
import org.questgame.webquestgamespring.util.ChatGPTHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
public class InitializationService {

	public String init(Model model, HttpSession session) {
		Story story = (Story) session.getAttribute("story");
		ElementManager em = new ElementManager(story);

		session.setAttribute("elementManager", em);
		session.setAttribute("story", story);
		model.addAttribute("currentSituation", em.getMainSituation());
		log.info("Attribute story, element-manager and first situation added to session");

		log.info("Redirecting to index.html");
		return "index";
	}

	public String generateStory(String userPrompt, HttpSession session) {
		log.info("Received user prompt: {}, started generating story", userPrompt);
		String gptResponse = ChatGPTHandler.sendRequest(userPrompt);
		log.info("ChatGpt Response: {}", ChatGPTHandler.getLastFullResponse());
		Story story = ElementInitializer.createStoryFromAiResponse(gptResponse);
		session.setAttribute("story", story);

		log.info("Redirecting to /init");
		return "forward:/init";
	}

}
