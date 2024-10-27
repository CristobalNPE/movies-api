package dev.cnpe.moviesapi.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;


//Evitamos usar Lombok en entidades
//toString() No debe contener campos con relaciones LAZY (colecciones por defecto)
//equals() ans hashCode() Solo deberían depender de Id
@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Double weight;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Set<Movie> getAssociatedMovies() {
        return associatedMovies;
    }

    public void setAssociatedMovies(Set<Movie> associatedMovies) {
        this.associatedMovies = associatedMovies;
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
