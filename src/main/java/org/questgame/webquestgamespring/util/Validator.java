package org.questgame.webquestgamespring.util;

public class Validator {

	public static boolean isValidId(Long id) {
		return id > 0;
	}

	public static boolean isValidUsername(String username) {
		return username != null && ! username.isEmpty() && ! username.isBlank() && username.length() < 50;
	}
}
