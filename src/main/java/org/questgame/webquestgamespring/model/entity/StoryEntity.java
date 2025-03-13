package org.questgame.webquestgamespring.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "quests")
public class StoryEntity {
	private String id;

	@NonNull
	private String name;

	@NonNull
	private String description;

	@NonNull
	private Long userId;

	@NonNull
	private Binary elements;

	public StoryEntity(@NonNull Binary elements) {
		this.elements = elements;
	}

	public StoryEntity(@NonNull String name,@NonNull String description,@NonNull Long userId,@NonNull Binary elements) {
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.elements = elements;
	}
}
