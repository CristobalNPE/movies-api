package dev.cnpe.moviesapi.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CharacterCreationRequest(

        @NotBlank
        String image,

        @NotBlank
        String name,

        @Min(0) @Max(150)
        Integer age,

        @Min(0) @Max(1000)
        Double weight,

        @NotBlank @Size(min = 10, max = 1000)
        String story
) {
        public CharacterCreationRequest {
        }
}
