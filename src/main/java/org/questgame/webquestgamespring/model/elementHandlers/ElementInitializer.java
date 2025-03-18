package org.questgame.webquestgamespring.model.elementHandlers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.questgame.webquestgamespring.model.exceptions.ChoiceInitializeException;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.storyElements.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Setter
@Getter
public class ElementInitializer {

	private HashMap<String, Element> elements = new HashMap<>();

	private String story;

	public static Story createStoryFromAiResponse(String json) {
		ElementInitializer el = new ElementInitializer(json);
		el.initializeAllElements();
		el.setChoicesToSituation();
		el.setNextSituationForChoice();
		log.info("Story from json initialized");
		return new Story(el.getElements());
	}

	private void initializeAllElements() {
		log.info("Started initializing elements from story");
		initializeElements("'Ситуация': {", "]", this::initializeSituation);
		initializeElements("'Победа': {", "},", this::initializeVictory);
		initializeElements("'Поражение': {", "}", this::initializeFail);
		initializeElements("'Выборы ситуации': [", "]", this::initializeChoices);
	}

	private void initializeElements(String startToken, String endToken, Consumer<String> elementCreator) {
		String localStory = story;
		while (localStory.contains(startToken)) {
			String elementBlock = StringUtils.substringBetween(localStory, startToken, endToken);
			elementCreator.accept(elementBlock);
			localStory = StringUtils.substringAfter(localStory, startToken);
		}
	}

	private void initializeSituation(String situationBlock) {
		String description;
		String situationIndex;
		String leadFromIndex;

		description = StringUtils.substringBetween(situationBlock, "'Описание': '", "',");
		situationIndex = StringUtils.substringBetween(situationBlock, "'Индекс ситуации': '", "',");
		leadFromIndex = StringUtils.substringBetween(situationBlock, "'Привело из': '", "',");

		if (leadFromIndex.equals("-")) {
			elements.put(situationIndex, new MainSituation(description));
			log.info("MainSituation initialized, with index: {}, description: {}", situationIndex, description);
		} else {
			elements.put(situationIndex, new Situation(description));
			log.info("Situation initialized, with index: {}, description: {}", situationIndex, description);
		}
	}

	private void initializeVictory(String victoryBlock) {
		String description;
		String victoryIndex;

		description = StringUtils.substringBetween(victoryBlock, "'Описание': '", "',");
		victoryIndex = StringUtils.substringBetween(victoryBlock, "'Индекс победы': '", "',");

		elements.put(victoryIndex, new Victory(description));
		log.info("Victory initialized, with index: {}, description: {}", victoryIndex, description);
	}

	private void initializeFail(String failBlock) {
		String description;
		String failIndex;

		description = StringUtils.substringBetween(failBlock, "'Описание': '", "',");
		failIndex = StringUtils.substringBetween(failBlock, "'Индекс поражения': '", "',");
		elements.put(failIndex, new Fail(description));
		log.info("Fail initialized, with index: {}, description: {}", failIndex, description);
	}

	private void initializeChoices(String choiceBlock) {
		String description;
		String choiceIndex;
		String flag;

		while (choiceBlock.contains("{")) {
			String choice = StringUtils.substringBetween(choiceBlock, "{", "}");

			description = StringUtils.substringBetween(choice, "'Описание': '", "',");
			choiceIndex = StringUtils.substringBetween(choice, "'Индекс выбора': '", "',");
			flag = StringUtils.substringBetween(choice, "'Флаг': '", "',");

			elements.put(choiceIndex, new Choice(description, flagHandler(flag)));
			log.info("Choice initialized, with index: {}, description: {}, goNext: {}", choiceIndex, description, flag);

			choiceBlock = StringUtils.substringAfter(choiceBlock, "{");
		}
	}

	private boolean flagHandler(String flag) {
		switch (flag.toLowerCase()) {
			case "gonext", "victory", "fail" -> {
				return true;
			}
			default -> {
				return false;
			}
		}
	}

	private void setNextSituationForChoice() {
		log.info("Setting next situations to choices");
		Map<String, Element> choiceMap = elements.entrySet().stream()
				.filter((entry) -> entry.getValue().getClass().equals(Choice.class))
				.collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		for (String choiceIndex : choiceMap.keySet()) {
			Choice choice = (Choice) choiceMap.get(choiceIndex);

			if (!choice.isGoNext()) {
				choice.setLeadTo(choice.getLeadFrom());
				log.info("Choice {} lead to Situation {} ", choiceIndex, choice.getLeadFrom().getDescription());
				continue;
			}

			String choiceBlock = StringUtils.substringBetween(story, "'Индекс выбора': '" + choiceIndex + "'", "}");
			String leadToIndex = StringUtils.substringBetween(choiceBlock, "'Ведет к': '", "'");

			if (leadToIndex.equals("-")) {
				log.error("Choice {} leadTo \"-\"", choiceIndex);
				throw new ChoiceInitializeException("Choice " + choiceIndex + "leadTo \"-\"");
			}
			choice.setLeadTo((Situation) elements.get(leadToIndex));
			Situation leadToSituation = (Situation) elements.get(leadToIndex);
			leadToSituation.addLeadFrom(choice);
			log.info("Choice {} lead to Situation {} ", choiceIndex, leadToIndex);
		}
	}

	private void setChoicesToSituation() {
		log.info("Setting Choices to Situations");
		Map<String, Element> situationMap = elements.entrySet().stream()
				.filter(entry -> entry.getValue().getClass().equals(MainSituation.class)
						|| entry.getValue().getClass().equals(Situation.class))
				.collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

		for (String situationIndex : situationMap.keySet()) {
			MainSituation situation = (MainSituation) elements.get(situationIndex);
			String situationBlock = StringUtils.substringBetween(story, "'Индекс ситуации': '" + situationIndex + "',", "]");
			while (situationBlock.contains("'Индекс выбора'")) {
				String choiceIndex = StringUtils.substringBetween(situationBlock, "Индекс выбора': '", "'");
				if (elements.containsKey(choiceIndex)) {
					Choice c = (Choice) elements.get(choiceIndex);
					situation.addChoice(c);
					c.setLeadFrom(situation);
					log.info("Situation {} and choice {} linked", situationIndex, choiceIndex);
				} else {
					log.error("element map doesn't contain key {}", choiceIndex);
					throw new NoSuchElementException();
				}
				situationBlock = StringUtils.substringAfter(situationBlock, "'Индекс выбора'");
			}
		}
	}

	public ElementInitializer() {
		log.info("ElementInitializer created with no params");
	}

	public ElementInitializer(String json) {
		this.story = json;
		log.info("ElementInitializer created with json");
	}
}