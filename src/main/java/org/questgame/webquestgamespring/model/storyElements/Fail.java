package org.questgame.webquestgamespring.model.storyElements;

import java.io.Serializable;

public class Fail extends Situation implements Serializable {

	public Fail(String description, Choice[] leadFrom) {
		super(description, leadFrom);
	}

	public Fail(String description) {
		super(description);
	}
}
