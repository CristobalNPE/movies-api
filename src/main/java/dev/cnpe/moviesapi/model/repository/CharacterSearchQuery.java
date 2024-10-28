package dev.cnpe.moviesapi.model.repository;

import dev.cnpe.moviesapi.model.dto.CharacterSearchCriteria;
import dev.cnpe.moviesapi.model.dto.CharacterSearchResult;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CharacterSearchQuery {

    private final EntityManager entityManager;

    public List<CharacterSearchResult> search(CharacterSearchCriteria searchCriteria) {

        // @formatter:off
        String jpql =
                "SELECT new dev.cnpe.moviesapi.model.dto.CharacterSearchResult(c.id, c.name, c.image)" +
                " FROM Character c" +
                (searchCriteria.movieTitle() != null ? " LEFT JOIN c.movies m" : "") +
                " WHERE ";
        // @formatter:on

        List<String> jpqlParts = new ArrayList<>();
        jpqlParts.add("1=1");

        Map<String, Object> params = new HashMap<>();

        if (searchCriteria.name() != null) {
            jpqlParts.add("UPPER(c.name) LIKE UPPER('%' || :name || '%')");
            params.put("name", searchCriteria.name());
        }
        if (searchCriteria.age() != null) {
            jpqlParts.add("c.age = :age");
            params.put("age", searchCriteria.age());
        }
        if (searchCriteria.weight() != null) {
            jpqlParts.add("c.weight = :weight");
            params.put("weight", searchCriteria.weight());
        }
        if (searchCriteria.movieTitle() != null) {
            jpqlParts.add("UPPER(m.title) LIKE UPPER('%' || :movieTitle || '%')");
            params.put("movieTitle", searchCriteria.movieTitle());
        }

        String whereSearchCriteria = String.join(" AND ", jpqlParts);

        var query = entityManager.createQuery(jpql + whereSearchCriteria, CharacterSearchResult.class);
        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query.getResultList();
    }

}
