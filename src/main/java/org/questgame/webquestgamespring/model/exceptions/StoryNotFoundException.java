package org.questgame.webquestgamespring.model.exceptions;

public class StoryNotFoundException extends RuntimeException {
	public StoryNotFoundException() {
	}

	public StoryNotFoundException(String message) {
		super(message);
	}

	public StoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoryNotFoundException(Throwable cause) {
		super(cause);
	}

	public StoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
