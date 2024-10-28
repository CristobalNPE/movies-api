package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record CharacterSearchCriteria(
        String name,
        String age,
        String weight,
        String movieTitle
) {
}
