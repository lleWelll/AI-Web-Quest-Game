package org.questgame.webquestgamespring.util;

import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.exceptions.SerializationException;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class StorySerializer {

	public static byte[] serialize(Object obj) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(obj);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void serialize(Story story, OutputStream outputStream) {
		log.info("Serializing Story on outputStream");
		try (ObjectOutputStream objectOut = new ObjectOutputStream(outputStream)) {
			objectOut.writeObject(story);
		} catch (IOException e) {
			log.error("Error in serializing, throwing Exception");
			throw new SerializationException("Error in serializing", e);
		}
	}
	public static Object deserializeFromFile(byte[] bytes) {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			 ObjectInputStream ois = new ObjectInputStream(bis)) {
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new SerializationException(e);
		}
	}
	public static Story getStoryFromFile(String path) {
		if (path == null) {
			log.error("File is null");
			throw new NullPointerException("File is null");
		}
		return deserializeFromFile(path);
	}

	private static Story deserializeFromFile(String path) {
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
