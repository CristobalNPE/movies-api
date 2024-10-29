package dev.cnpe.moviesapi.model.repository;

import dev.cnpe.moviesapi.model.dto.CharacterSearchCriteria;
import dev.cnpe.moviesapi.model.dto.CharacterSearchResult;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CharacterSearchQuery {

    private final EntityManager entityManager;

    public Page<CharacterSearchResult> search(CharacterSearchCriteria searchCriteria, Pageable pageable) {

        // @formatter:off
        String jpql =
                "SELECT new dev.cnpe.moviesapi.model.dto.CharacterSearchResult(c.id, c.name, c.image)" +
                " FROM Character c" +
                (searchCriteria.movieTitle() != null ? " LEFT JOIN c.movies m" : "") +
                " WHERE ";

        //Necesario para paginación
        String countJpql =
                "SELECT COUNT(DISTINCT c)" +
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

        String whereClause = String.join(" AND ", jpqlParts);

        var countQuery = entityManager.createQuery(countJpql + whereClause, Long.class);
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();


        var dataQuery = entityManager.createQuery(jpql + whereClause + buildOrderByClause(pageable), CharacterSearchResult.class);
        params.forEach(dataQuery::setParameter);

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        List<CharacterSearchResult> results = dataQuery.getResultList();
        return new PageImpl<>(results, pageable, total);
    }

    private String buildOrderByClause(Pageable pageable) {
        //<editor-fold desc="Imperative/No Warning">
        //        if (!pageable.getSort().isSorted()) {
//            return "";
//        }
//
//        List<String> sortOrders = new ArrayList<>();
//        pageable.getSort().forEach(order ->
//                sortOrders.add("c." + order.getProperty() + " " + order.getDirection())
//        );
//
//        return " ORDER BY " + String.join(", ", sortOrders);
        //</editor-fold>

        return pageable.getSort().isSorted()
                ? pageable.getSort().stream()
                          .map(order -> "c." + order.getProperty() + " " + order.getDirection())
                          .collect(Collectors.joining(", ", " ORDER BY ", ""))
                : "";


    }


}
