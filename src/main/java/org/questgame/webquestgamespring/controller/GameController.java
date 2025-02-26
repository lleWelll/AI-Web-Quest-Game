package org.questgame.webquestgamespring.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.StorySerializer;
import org.questgame.webquestgamespring.model.elementHandlers.ElementInitializer;
import org.questgame.webquestgamespring.model.elementHandlers.ElementManager;
import org.questgame.webquestgamespring.model.storyElements.Fail;
import org.questgame.webquestgamespring.model.storyElements.Situation;
import org.questgame.webquestgamespring.model.storyElements.Victory;
import org.questgame.webquestgamespring.util.ChatGPTHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping()
public class GameController {


	@GetMapping("/")
	public String welcome() {
		return "welcome-page";
	}

	@PostMapping("/generate")
	public String generateStory(@RequestParam String userPrompt, HttpSession session) {
		log.info("Received user prompt: {}, started generating story", userPrompt);
		String gptResponse = ChatGPTHandler.sendRequest(userPrompt);
		log.info("ChatGpt Response: {}", ChatGPTHandler.getLastFullResponse());

		Story story = ElementInitializer.createStoryFromAiResponse(gptResponse);
		session.setAttribute("story", story);

		log.info("Redirecting to /init");
		return "forward:/init";
	}

	@PostMapping("/init")
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

	@GetMapping("/story")
	public String move(@RequestParam Integer index, Model model, HttpSession session) {
		log.info("Updating current situation");
		ElementManager em = (ElementManager) session.getAttribute("elementManager");


		if (index == null) throw new RuntimeException("Index of choice is null");
		if (index == -1) model.addAttribute("currentSituation", em.getPreviousSituation());
		else model.addAttribute("currentSituation", em.getNextSituation(index));
		log.info("Current situation = {} (choice with index {})", em.getCurrentSituation().getDescription(), index);

		if (em.getCurrentSituation() instanceof Victory || em.getCurrentSituation() instanceof Fail) {
			log.info("Redirecting to /finish");
			return "redirect:/finish"; //redirect to /finish
		}
		else {
			log.info("Redirecting to index");
			return "index";
		}
	}

	@GetMapping("/finish")
	public String finish(Model model, HttpSession session) {
		log.info("Checking for victory or fail");
		ElementManager em = (ElementManager) session.getAttribute("elementManager");
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

	@GetMapping("/restart")
	public String restartGame(HttpSession session) {
		log.info("Restarting current session");
		session.invalidate();
		log.info("Redirecting to welcome.html");
		return "forward:/";
	}

	@GetMapping("/restartStory")
	public String restartCurrentStory(Model model, HttpSession session) {
		log.info("Restarting game with current story");
		ElementManager em = (ElementManager) session.getAttribute("elementManager");
		model.addAttribute("currentSituation", em.getMainSituation());
		log.info("Current Situation = MainSituation, redirecting to index.html");
		return "index";
	}

	@GetMapping("/download")
	public void downloadStory(HttpSession session, HttpServletResponse resp) throws IOException {
		log.info("Preparing to download story");
		LocalDateTime currentDateTime = LocalDateTime.now();
		String fileName = "story-" + currentDateTime + ".ser";
		Story story =  (Story) session.getAttribute("story");
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		log.info("File {} is prepared to download", fileName);
		StorySerializer.serialize(story, resp.getOutputStream());
	}

	@PostMapping("/upload")
	public String uploadStory(HttpServletRequest req, HttpSession session) throws ServletException, IOException {
		log.info("Sending file on server");
		String uploadPath = "/Users/llwll/Downloads";

		String uploadedPath = uploadFile(req, uploadPath);
		session.setAttribute("story", StorySerializer.getStoryFromFile(uploadedPath));

		log.info("Redirecting to /init");
		return "forward:/init";
	}

	private String uploadFile(HttpServletRequest req, String uploadPath) throws ServletException, IOException {
		for (Part part : req.getParts()) {
			String fileName = part.getSubmittedFileName();
			if (fileName != null) {
				part.write(uploadPath + File.separator + fileName);
				log.info("File {} uploaded to server", fileName);
				return uploadPath + "/" + fileName;
			}
		}
		log.error("There is no file in request");
		throw new FileNotFoundException("There is no file in request");
	}

}
