package com.BookMyShow.models;

import com.BookMyShow.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(unique = true)
    private String movieName;

    private Double duration;

    private LocalDate releaseDate;

    @Enumerated(value = EnumType.STRING)
    private Language language;

    private Double rating;

    @OneToMany(mappedBy = "Movie",cascade = CascadeType.ALL)
    private List<Show> showList;

}
