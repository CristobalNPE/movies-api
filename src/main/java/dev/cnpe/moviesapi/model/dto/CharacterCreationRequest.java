package dev.cnpe.moviesapi.model.dto;

import lombok.Builder;

@Builder
public record CharacterCreationRequest(

        String image,
        String name,
        Integer age,
        Double weight,
        String story
) {


}
