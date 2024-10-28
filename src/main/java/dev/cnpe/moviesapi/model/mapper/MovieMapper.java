package dev.cnpe.moviesapi.model.mapper;

import dev.cnpe.moviesapi.model.dto.MovieBasicResponse;
import dev.cnpe.moviesapi.model.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieBasicResponse toBasicResponse(Movie movie);

}
