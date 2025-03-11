package org.questgame.webquestgamespring.util;

import org.questgame.webquestgamespring.model.storyElements.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class Serializer {

	public static byte[] serialize(Map<String, Element> STORY_ELEMENTS) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(STORY_ELEMENTS);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
