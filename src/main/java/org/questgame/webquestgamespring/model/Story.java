package org.questgame.webquestgamespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.storyElements.Element;

import java.io.*;
import java.util.Map;


@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story implements Serializable {

	private Map<String, Element> STORY_ELEMENTS;

}
