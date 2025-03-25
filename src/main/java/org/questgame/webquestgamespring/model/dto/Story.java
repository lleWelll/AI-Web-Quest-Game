package org.questgame.webquestgamespring.model.dto;

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
@AllArgsConstructor
public class Story implements Serializable {

	private Map<String, Element> STORY_ELEMENTS;

}
