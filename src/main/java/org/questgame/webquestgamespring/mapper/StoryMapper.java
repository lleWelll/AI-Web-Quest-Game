package org.questgame.webquestgamespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.questgame.webquestgamespring.model.dto.StoryNameAndDescription;
import org.questgame.webquestgamespring.model.dto.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.storyElements.Element;
import org.questgame.webquestgamespring.util.StorySerializer;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface StoryMapper {

	@Mapping(source = "quest", target = "STORY_ELEMENTS", qualifiedByName = "toMap")
	Story entityToStory(StoryEntity entity);

	@Mapping(source = "STORY_ELEMENTS", target = "quest", qualifiedByName = "toBinary")
	StoryEntity storyToEntity(Story story);

	StoryEntity storyNameAndDescriptionToEntity(StoryNameAndDescription story);

	StoryNameAndDescription entityToStoryNameAndDescription(StoryEntity entity);

	@Named("toMap")
	default Map<String, Element> bytesToMap(byte[] elements) {
		return (Map<String, Element>) StorySerializer.deserializeFromFile(elements);
	}

	@Named("toBinary")
	default byte[] mapToBytes(Map<String, Element> elements) {
		return StorySerializer.serialize(elements);
	}
}
