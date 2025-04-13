package org.questgame.webquestgamespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.questgame.webquestgamespring.model.dto.story.Story;
import org.questgame.webquestgamespring.model.entity.StoryEntity;
import org.questgame.webquestgamespring.model.exceptions.SerializationException;
import org.questgame.webquestgamespring.model.storyElements.Element;
import org.questgame.webquestgamespring.util.StorySerializer;

import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface StoryMapper {

	@Mapping(target = "quest", source = "storyElements", qualifiedByName = "serializeMap")
	StoryEntity toStoryEntity(Story dto);

	@Mapping(target = "storyElements", source = "quest", qualifiedByName = "deserializeMap")
	Story toStoryDto(StoryEntity entity);

	@Named("deserializeMap")
	default Map<String, Element> deserializeMap(byte[] bytes) {
		Object deserialize = StorySerializer.deserialize(bytes);

		if (deserialize instanceof Map<?, ?> rawMap) {
			Map<String, Element> result = new HashMap<>();
			for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
				if (entry.getKey() instanceof String key && entry.getValue() instanceof Element element) {
					result.put(key, element);
				} else {
					throw new SerializationException("Could not deserialize byes to map");
				}
			}
			return result;
		}
		else {
			throw new SerializationException("Could not deserialize byes to map");
		}
	}

	@Named("serializeMap")
	default byte[] serializeMap(Map<String, Element> map) {
		return StorySerializer.serialize(map);
	}

}
