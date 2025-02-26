package org.questgame.webquestgamespring.model.storyElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Situation extends MainSituation implements Serializable {

	private Choice[] leadFrom;

	public void addLeadFrom(Choice choice) {
		if (choice == null) throw new IllegalArgumentException("Choice can't be null");
		leadFrom = ArrayUtils.add(leadFrom, choice);
	}

	public Situation(String description, Choice[] choices, Choice[] leadFrom) {
		super(description, choices);
		this.leadFrom = leadFrom;
	}

	public Situation(String description) {
		super(description);
	}

	public Situation(String description, Choice[] leadFrom) {
		super(description);
		this.leadFrom = leadFrom;
	}
}
