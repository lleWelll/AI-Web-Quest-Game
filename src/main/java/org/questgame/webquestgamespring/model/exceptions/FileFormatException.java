package org.questgame.webquestgamespring.model.exceptions;

public class FileFormatException extends RuntimeException {
	public FileFormatException() {
		super();
	}

	public FileFormatException(String message) {
		super(message);
	}

	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
