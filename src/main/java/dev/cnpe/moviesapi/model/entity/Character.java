package dev.cnpe.moviesapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;


//Evitamos usar Lombok en entidades para:
//toString() No debe contener campos con relaciones LAZY (colecciones por defecto)
//equals() ans hashCode() Solo deberían depender de Id
@Setter
@Getter
@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "story")
    private String story;

    @ManyToMany
    @JoinTable(
            name = "movies_characters",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> associatedMovies = new HashSet<>();


    public Character(Long id, String image, String name, Integer age, Double weight, String story, Set<Movie> associatedMovies) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.story = story;
        this.associatedMovies = associatedMovies;
    }

    public Character() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character character)) return false;
        return Objects.equals(id, character.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Character.class.getSimpleName() + "[", "]")
                .add("story='" + story + "'")
                .add("weight=" + weight)
                .add("age=" + age)
                .add("name='" + name + "'")
                .add("image='" + image + "'")
                .add("id=" + id)
                .toString();
    }
}
