package org.questgame.webquestgamespring.model.elementHandlers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.dto.story.Story;
import org.questgame.webquestgamespring.model.storyElements.*;

import java.util.Stack;

@Slf4j
@Getter
@Setter
public class ElementManager {

	private final Story STORY;

	private final Stack<MainSituation> elementStack = new Stack<>();

	public MainSituation getCurrentSituation() {
		log.info("Returning current situation ({})", elementStack.peek().getDescription());
		return elementStack.peek();
	}

	public MainSituation getPreviousSituation() {
		if (elementStack.size() <= 1) {
			return getMainSituation();
		}
		elementStack.pop();
		log.info("Returning previous situation ({})", elementStack.peek().getDescription());
		return elementStack.peek();
	}

	public MainSituation getNextSituation(int index) {
		if (index > getCurrentChoices().length) throw new IllegalArgumentException("Index is invalid, " + index + " > " + getCurrentChoices().length);
		MainSituation next = elementStack.peek().getChoices()[index].goNext();
		elementStack.push(next);
		log.info("Returning next situation ({})", next.getDescription());
		return elementStack.peek();
	}

	public MainSituation getMainSituation() {
		elementStack.push((MainSituation) STORY.getSTORY_ELEMENTS().get("S1"));
		log.info("Returning Main Situation ({})", elementStack.peek().getDescription());
		return elementStack.peek();
	}

	public Choice[] getCurrentChoices() {
		log.info("Returning choices for Situation {}", elementStack.peek().getDescription());
		return elementStack.peek().getChoices();
	}

	public ElementManager(Story story) {
		log.info("Element Manager created");
		this.STORY = story;
	}
}
