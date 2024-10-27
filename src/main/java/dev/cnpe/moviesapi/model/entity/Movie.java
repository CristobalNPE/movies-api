package dev.cnpe.moviesapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rating")
    private Rating rating;

    @ManyToMany(mappedBy = "movies")
    private Set<Character> characters = new HashSet<>();

    public Movie(Long id, String title, LocalDate creationDate, Rating rating, Set<Character> characters) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.rating = rating;
        this.characters = characters;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Movie.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("creationDate=" + creationDate)
                .add("rating=" + rating)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Getter
    @RequiredArgsConstructor
    public enum Rating {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final int value;
    }


}
