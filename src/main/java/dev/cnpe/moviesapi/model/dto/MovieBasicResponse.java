package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record MovieBasicResponse(
        Long id,
        String title,
        String image
) {
}
