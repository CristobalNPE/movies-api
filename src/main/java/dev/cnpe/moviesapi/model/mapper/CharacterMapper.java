package dev.cnpe.moviesapi.model.mapper;

import dev.cnpe.moviesapi.model.dto.CharacterCreationRequest;
import dev.cnpe.moviesapi.model.dto.CharacterResponse;
import dev.cnpe.moviesapi.model.entity.Character;
import dev.cnpe.moviesapi.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface CharacterMapper {

    @Mapping(target = "movies", source = "movies")
    CharacterResponse toResponse(Character character);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", expression = "java(initMovies())")
    Character toEntity(CharacterCreationRequest creationRequest);


    default Set<Movie> initMovies() {
        return new HashSet<>();
    }
}
