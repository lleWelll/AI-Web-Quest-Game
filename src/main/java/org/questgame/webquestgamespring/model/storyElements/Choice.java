package org.questgame.webquestgamespring.model.storyElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.questgame.webquestgamespring.model.interfaces.StepProcessor;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Choice extends Element implements StepProcessor<MainSituation>, Serializable {

	private MainSituation leadFrom;

	private MainSituation leadTo;

	private boolean goNext;

	@Override
	public MainSituation goNext() {
		if (goNext) return leadTo;
		else return leadFrom;
	}

	public Choice(String description, Situation leadFrom, Situation leadTo, boolean goNext) {
		super(description);
		this.leadFrom = leadFrom;
		this.leadTo = leadTo;
		this.goNext = goNext;
	}

	public Choice(String description, boolean goNext) {
		super(description);
		this.goNext = goNext;
	}
}
