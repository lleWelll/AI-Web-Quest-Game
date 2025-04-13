package org.questgame.webquestgamespring.util;


import org.apache.commons.lang3.StringUtils;

public class Validator {

	public static boolean isValidId(Long id) {
		return id > 0;
	}

	public static boolean isValidUsername(String username) {
		return username != null && ! username.isEmpty() && ! username.isBlank() && username.length() < 50;
	}


	public static boolean isValidPassword(String password) {
		if (StringUtils.containsAny(password, '|', '\\', '/', '-', '+', '=', '.', ',', '^', '`', '~', ' ')) {
			return false;
		}
		return password.length() >= 8;
	}
	public static boolean confirmPassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
}
