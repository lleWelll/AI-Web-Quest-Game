package org.questgame.webquestgamespring.util;

import org.questgame.webquestgamespring.model.storyElements.Element;

import java.io.*;
import java.util.Map;

public class Deserializer {

	public static Map<String, Element> deserialize(byte[] elements) {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(elements);
			 ObjectInputStream ois = new ObjectInputStream(bis)) {
			return (Map<String, Element>) ois.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
