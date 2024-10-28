package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.dto.CharacterResponse;
import dev.cnpe.moviesapi.model.dto.CharacterSearchCriteria;
import dev.cnpe.moviesapi.model.dto.CharacterSearchResult;
import dev.cnpe.moviesapi.model.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public List<CharacterSearchResult> advancedSearch(@RequestBody CharacterSearchCriteria searchCriteria) {
        return characterService.search(searchCriteria);
    }


    @GetMapping
    public List<CharacterSearchResult> searchCharacters(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Integer age,
                                                        @RequestParam(required = false) Double weight,
                                                        @RequestParam(required = false) String movieTitle) {

        return characterService.search(new CharacterSearchCriteria(name, age, weight, movieTitle));
    }


    @GetMapping("/{id}")
    public CharacterResponse getCharacterById(@PathVariable(name = "id") Long id) {
        return characterService.getCharacterById(id);
    }

}
