package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.dto.*;
import dev.cnpe.moviesapi.model.entity.Character;
import dev.cnpe.moviesapi.model.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public String searchCharacters(Model model,
                                   Pageable pageable,
                                   @ModelAttribute CharacterSearchCriteria searchCriteria) {

        Page<CharacterSearchResult> characters = characterService.search(pageable, searchCriteria);

        model.addAttribute("characters", characters);
        model.addAttribute("searchCriteria", searchCriteria);

        return "characters/list";

    }

    @GetMapping("/{id}")
    public String getCharacterById(Model model, @PathVariable(name = "id") Long id) {

        CharacterResponse character = characterService.getCharacterById(id);
        model.addAttribute("character", character);

        return "characters/detail";
    }


    @GetMapping("/{id}/edit")
    public String editCharacterForm(Model model, @PathVariable(name = "id") Long id) {
        CharacterResponse character = characterService.getCharacterById(id);
        model.addAttribute("character", character);
        model.addAttribute("isNewCharacter", false);
        return "characters/form";
    }

    @GetMapping("/new")
    public String newCharacterForm(Model model) {
        CharacterCreationRequest emptyForm = CharacterCreationRequest.builder()
                                                                     .image("")
                                                                     .name("")
                                                                     .story("")
                                                                     .build();

        model.addAttribute("character", emptyForm);
        model.addAttribute("isNewCharacter", true);
        return "characters/form";
    }

    @PostMapping
    public String createCharacter(@Valid @ModelAttribute("character") CharacterCreationRequest form,
                                  BindingResult result,
                                  Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isNewCharacter", true);
            return "characters/form";
        }

        Character character = characterService.createCharacter(form);
        return "redirect:/characters/" + character.getId();
    }

    @PostMapping("/{id}")
    public String updateCharacter(@PathVariable Long id,
                                  @Valid @ModelAttribute("characterForm") CharacterUpdateRequest form,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isNewCharacter", false);
            return "characters/form";
        }

        characterService.updateCharacter(form, id);
        return "redirect:/characters/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return "redirect:/characters";
    }

}
