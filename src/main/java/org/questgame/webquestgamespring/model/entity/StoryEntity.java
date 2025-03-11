package org.questgame.webquestgamespring.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.Binary;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.util.StorySerializer;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "quests")
public class StoryEntity {
	private String id;

	private String name;

	private String description;

	private Long userId;

	private Binary elements;

	public StoryEntity(Binary elements) {
		this.elements = elements;
	}
}
