package com.example.kim_movie.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long movieId;

    @Column(nullable = false, length = 100)
    @Setter
    private String movieTitle;

    @Column(nullable = false, length = 1000)
    @Setter
    private String overview;

    @Setter
    private String releaseDate;

    @Setter
    private String posterPath;

    @Column(nullable = false)
    @Setter
    private Integer likePoint = 0;
}
