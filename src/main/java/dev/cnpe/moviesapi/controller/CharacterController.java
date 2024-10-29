package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;
import dev.cnpe.moviesapi.model.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public Page<CharacterSearchResult> searchCharacters(Pageable pageable,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Integer age,
                                                        @RequestParam(required = false) Double weight,
                                                        @RequestParam(required = false) String movieTitle) {

        return characterService.search(pageable, new CharacterSearchCriteria(name, age, weight, movieTitle));

    }

    @GetMapping("/{id}")
    public CharacterResponse getCharacterById(@PathVariable(name = "id") Long id) {
        return characterService.getCharacterById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createCharacter(@RequestBody CharacterCreationRequest creationRequest, UriComponentsBuilder ucb) {

        Character createdCharacter = characterService.createCharacter(creationRequest);

        URI location = ucb.path("/api/v1/characters/{id}").buildAndExpand(createdCharacter.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateCharacter(@RequestBody CharacterUpdateRequest updateRequest, @PathVariable Long id) {
        characterService.updateCharacter(updateRequest, id);
    }
}
