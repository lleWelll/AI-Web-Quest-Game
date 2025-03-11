package org.questgame.webquestgamespring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.questgame.webquestgamespring.util.Serializer;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "quests")
public class StoryEntity {
	private String id;
	private Binary elements;

	public StoryEntity(Story story) {
		this.elements = new Binary(Serializer.serialize(story.getSTORY_ELEMENTS()));
	}
}
