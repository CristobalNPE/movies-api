package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record CharacterUpdateRequest(
        String image,
        String name,
        Integer age,
        Double weight,
        String story
) {
}
