package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record CharacterSearchResult(Long id, String name, String image) {
}
