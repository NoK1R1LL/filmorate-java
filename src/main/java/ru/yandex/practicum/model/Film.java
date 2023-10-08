package ru.yandex.practicum.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;

    @Past(message = "Дата релиза должна быть в прошлом")
    private LocalDate releaseDate; // Изменили тип на LocalDate

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    @ManyToMany
    @JoinTable(
            name = "film_likes",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    @Transient
    private LocalDate releaseDateAsLocalDate;

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        this.releaseDateAsLocalDate = releaseDate;
    }
}

