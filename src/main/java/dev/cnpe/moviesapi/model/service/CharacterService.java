package dev.cnpe.moviesapi.model.service;

import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CharacterService {

    Page<CharacterSearchResult> search(Pageable pageable, CharacterSearchCriteria searchCriteria);

    CharacterResponse getCharacterById(Long id);

    Character createCharacter(CharacterCreationRequest creationRequest);

    void updateCharacter(CharacterUpdateRequest updateRequest, Long id);

    void deleteCharacter(Long id);

}
