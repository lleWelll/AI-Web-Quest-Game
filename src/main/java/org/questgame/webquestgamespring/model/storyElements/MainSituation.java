package org.questgame.webquestgamespring.model.storyElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MainSituation extends Element implements Serializable {

	private Choice[] choices;

	public void addChoice(Choice choice) {
		if (choice == null) throw new IllegalArgumentException("Choice cannot be null");
		choices = ArrayUtils.add(choices, choice);
	}

	public MainSituation(String description, Choice[] choices) {
		super(description);
		this.choices = choices;
	}

	public MainSituation(String description) {
		super(description);
	}
}
