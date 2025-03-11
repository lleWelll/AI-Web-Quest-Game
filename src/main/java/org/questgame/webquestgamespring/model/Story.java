package org.questgame.webquestgamespring.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.storyElements.Element;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.*;
import java.util.Map;


@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Story implements Serializable {

	private Map<String, Element> STORY_ELEMENTS;

	public Story(Map<String, Element> STORY_ELEMENTS) {
		this.STORY_ELEMENTS = STORY_ELEMENTS;
	}
}
