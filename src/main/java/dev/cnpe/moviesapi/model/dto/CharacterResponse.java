package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record CharacterResponse(
        Long id,
        String name,
        String image,
        Integer age,
        Double weight,
        String story,
        Set<MovieBasicResponse> movies
) {
}
