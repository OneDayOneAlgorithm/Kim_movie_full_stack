package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenre {
    @Id
    @GeneratedValue
    private Long movieGenreId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_genre_movie_id")
    private Movie movieGenreMovieId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_genre_genre_id")
    private Genre movieGenreGenreId;
}
