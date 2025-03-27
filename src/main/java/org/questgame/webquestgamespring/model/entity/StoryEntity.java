package org.questgame.webquestgamespring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quests")
@Data
@NoArgsConstructor
public class StoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 25)
	private String name;

	@Lob
	private String description;

	@Lob
	@Column(name = "quest_binary")
	private byte[] quest;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	public StoryEntity(String name, String description, byte[] quest, UserEntity user) {
		this.name = name;
		this.description = description;
		this.quest = quest;
		this.user = user;
	}

	public StoryEntity(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
