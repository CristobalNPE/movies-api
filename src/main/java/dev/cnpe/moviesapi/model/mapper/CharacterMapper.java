package dev.cnpe.moviesapi.model.mapper;

import dev.cnpe.moviesapi.model.dto.CharacterResponse;
import dev.cnpe.moviesapi.model.entity.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface CharacterMapper {

    @Mapping(target = "movies", source = "movies")
    CharacterResponse toResponse(Character character);
}
