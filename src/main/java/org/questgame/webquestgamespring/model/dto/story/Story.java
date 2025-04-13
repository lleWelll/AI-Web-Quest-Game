package org.questgame.webquestgamespring.model.dto.story;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.questgame.webquestgamespring.model.storyElements.Element;

import java.io.*;
import java.util.Map;


@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story implements Serializable {

	private Map<String, Element> storyElements;

}
