package org.questgame.webquestgamespring.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.StorySerializer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class StoryFileService {

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
