package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record CharacterSearchCriteria(
        String name,
        Integer age,
        Double weight,
        String movieTitle
) {
}
