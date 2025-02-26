package org.questgame.webquestgamespring.model.storyElements;

import java.io.Serializable;

public class Victory extends Situation implements Serializable {

	public Victory(String description, Choice[] leadFrom) {
		super(description, leadFrom);
	}

	public Victory(String description) {
		super(description);
	}
}
