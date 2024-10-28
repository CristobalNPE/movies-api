package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.dto.CharacterSearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {


    @GetMapping
    public List<CharacterSearchResult> getAllCharacters(){
        return null;
    }




}
