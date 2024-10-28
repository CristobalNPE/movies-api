package dev.cnpe.moviesapi.model.repository;

import dev.cnpe.moviesapi.model.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {



}
