package org.questgame.webquestgamespring.util;

import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.exceptions.SerializationException;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class StorySerializer {

	public static void serialize(Story story, OutputStream outputStream) {
		log.info("Serializing Story on outputStream");
		try (ObjectOutputStream objectOut = new ObjectOutputStream(outputStream)) {
			objectOut.writeObject(story);
		} catch (IOException e) {
			log.error("Error in serializing, throwing Exception");
			throw new SerializationException("Error in serializing", e);
		}
	}

	public static Story getStoryFromFile(String path) {
		if (path == null) {
			log.error("File is null");
			throw new NullPointerException("File is null");
		}
		return deserialize(path);
	}

	private static Story deserialize(String path) {
		log.info("Deserializing Story object from file: ");
		try (FileInputStream fileIn = new FileInputStream(path);
			 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
			Story story = (Story)objectIn.readObject();
			log.info("Story object successfully deserialized");
			return story;
		} catch (IOException | ClassNotFoundException e) {
			log.error("Error in Deserializing Story", e);
			throw new SerializationException("Error in Deserializing Story", e);
		}
	}
}
