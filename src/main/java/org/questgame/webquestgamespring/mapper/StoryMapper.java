package org.questgame.webquestgamespring.mapper;

import org.bson.types.Binary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.questgame.webquestgamespring.model.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.storyElements.Element;
import org.questgame.webquestgamespring.util.StorySerializer;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface StoryMapper {

	@Mapping(source = "elements", target = "STORY_ELEMENTS", qualifiedByName = "toMap")
	Story toStory(StoryEntity entity);

	@Mapping(source = "STORY_ELEMENTS", target = "elements", qualifiedByName = "toBinary")
	StoryEntity toEntity(Story story);

	@Named("toMap")
	default Map<String, Element> binaryToMap(Binary elements) {
		return (Map<String, Element>) StorySerializer.deserializeFromFile(elements.getData());
	}

	@Named("toBinary")
	default Binary mapToBinary(Map<String, Element> elements) {
		return new Binary(StorySerializer.serialize(elements));
	}
}
