package dev.cnpe.moviesapi.model.service.impl;

import dev.cnpe.moviesapi.exception.DomainException;
import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;
import dev.cnpe.moviesapi.model.mapper.CharacterMapper;
import dev.cnpe.moviesapi.model.repository.CharacterRepository;
import dev.cnpe.moviesapi.model.repository.CharacterSearchQuery;
import dev.cnpe.moviesapi.model.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.cnpe.moviesapi.exception.DomainException.ErrorCode.CHARACTER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final CharacterSearchQuery searchQuery;
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public Page<CharacterSearchResult> search(Pageable pageable, CharacterSearchCriteria searchCriteria) {
        return searchQuery.search(searchCriteria,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
    }

    @Override
    public CharacterResponse getCharacterById(Long id) {
        return characterRepository.findById(id)
                                  .map(characterMapper::toResponse)
                                  .orElseThrow(() -> new DomainException(CHARACTER_NOT_FOUND));
    }

    @Override
    public Character createCharacter(CharacterCreationRequest creationRequest) {
        return characterRepository.save(characterMapper.toEntity(creationRequest));
    }

    @Override
    public void updateCharacter(CharacterUpdateRequest updateRequest, Long id) {
        Character character = characterRepository.findById(id)
                                                 .orElseThrow(() -> new DomainException(CHARACTER_NOT_FOUND));

        validateUpdate(character, updateRequest);
        updateEntityFromRequest(character, updateRequest);

        characterRepository.save(character);

    }

    @Override
    public void deleteCharacter(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new DomainException(CHARACTER_NOT_FOUND);
        }
        characterRepository.deleteById(id);
    }

    private void validateUpdate(Character character, CharacterUpdateRequest updateRequest) {
        /*
         * Checks entity state
         * Validates business rules // Not covered by @Validated?
         * Handles concurrency
         * Validates relationships
         * Throws specific exceptions
         * */
    }

    private void updateEntityFromRequest(Character character, CharacterUpdateRequest updateRequest) {
        Optional.ofNullable(updateRequest.image())
                .ifPresent(character::setImage);

        Optional.ofNullable(updateRequest.name())
                .ifPresent(character::setName);

        Optional.ofNullable(updateRequest.age())
                .ifPresent(character::setAge);

        Optional.ofNullable(updateRequest.weight())
                .ifPresent(character::setWeight);

        Optional.ofNullable(updateRequest.story())
                .ifPresent(character::setStory);

        /*   Updates if auditing:
         *    entity.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
         *    entity.setLastModifiedDate(LocalDateTime.now());
         * */
    }
}
