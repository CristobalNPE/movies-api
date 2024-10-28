package dev.cnpe.moviesapi.model.service;

import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;

import java.util.List;

public interface CharacterService {

    List<CharacterSearchResult> search(CharacterSearchCriteria searchCriteria);

    CharacterResponse getCharacterById(Long id);

    Character createCharacter(CharacterCreationRequest creationRequest);

    void updateCharacter(CharacterUpdateRequest updateRequest);

    void deleteCharacter(Long id);

}
