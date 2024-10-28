package dev.cnpe.moviesapi.model.service.impl;

import dev.cnpe.moviesapi.exception.DomainException;
import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;
import dev.cnpe.moviesapi.model.mapper.CharacterMapper;
import dev.cnpe.moviesapi.model.repository.CharacterRepository;
import dev.cnpe.moviesapi.model.repository.CharacterSearchQuery;
import dev.cnpe.moviesapi.model.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.cnpe.moviesapi.exception.DomainException.ErrorCode.CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final CharacterSearchQuery searchQuery;
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public List<CharacterSearchResult> search(CharacterSearchCriteria searchCriteria) {
        return searchQuery.search(searchCriteria);
    }

    @Override
    public CharacterResponse getCharacterById(Long id) {
        return characterRepository.findById(id)
                                  .map(characterMapper::toResponse)
                                  .orElseThrow(() -> new DomainException(CHARACTER_NOT_FOUND));
    }

    @Override
    public Character createCharacter(CharacterCreationRequest creationRequest) {
        return null;
    }

    @Override
    public void updateCharacter(CharacterUpdateRequest updateRequest) {

    }

    @Override
    public void deleteCharacter(Long id) {

    }
}
