package org.questgame.webquestgamespring.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.elementHandlers.ElementManager;
import org.questgame.webquestgamespring.model.storyElements.Fail;
import org.questgame.webquestgamespring.model.storyElements.Situation;
import org.questgame.webquestgamespring.model.storyElements.Victory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
public class StoryManagementService {

	public String move(Integer index, Model model, HttpSession session) {
		ElementManager em = getElementManager(session);
		if (index == null) throw new RuntimeException("Index of choice is null");
		if (index == -1) model.addAttribute("currentSituation", em.getPreviousSituation());
		else model.addAttribute("currentSituation", em.getNextSituation(index));
		log.info("Current situation = {} (choice with index {})", em.getCurrentSituation().getDescription(), index);
		if (em.getCurrentSituation() instanceof Victory || em.getCurrentSituation() instanceof Fail) {
			log.info("Redirecting to /finish");
			return "redirect:/finish";
		}
		else {
			log.info("Redirecting to index");
			return "index";
		}
	}

	public String finish(Model model, HttpSession session) {
		log.info("Checking for victory or fail");
		ElementManager em = getElementManager(session);
		model.addAttribute("currentSituation", em.getCurrentSituation());
		Situation currentSituation = (Situation) em.getCurrentSituation();
		if (currentSituation instanceof Victory) model.addAttribute("finishType", "victory");
		else if (currentSituation instanceof Fail) model.addAttribute("finishType", "fail");
		else {
			log.error("Current situation is not victory or fail ({})", currentSituation.getClass().getSimpleName());
			throw new IllegalArgumentException("Current situation is not victory or fail");
		}
		log.info("Redirecting to finish.html");
		return "finish";
	}

	public String restartGame(HttpSession session) {
		log.info("Restarting current session");
		session.invalidate();
		log.info("Redirecting to welcome.html");
		return "forward:/";
	}

	public String restartCurrentStory(Model model, HttpSession session) {
		log.info("Restarting game with current story");
		ElementManager em = getElementManager(session);
		model.addAttribute("currentSituation", em.getMainSituation());
		log.info("Current Situation = MainSituation, redirecting to index.html");
		return "index";
	}

	private ElementManager getElementManager(HttpSession session) {
		return (ElementManager) session.getAttribute("elementManager");
	}
}
