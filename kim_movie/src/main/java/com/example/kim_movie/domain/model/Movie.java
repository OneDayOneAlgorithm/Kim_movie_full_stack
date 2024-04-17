package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column
    @Setter
    private Integer likePoint = 0;

    @OneToMany(mappedBy = "dibsMovie", cascade = CascadeType.ALL)
    private List<MovieDibs> movieDibslist;

    @OneToMany(mappedBy = "likeMovie", cascade = CascadeType.ALL)
    private List<MovieLike> movieLikelist;

    @OneToMany(mappedBy = "movieReviewMovie", cascade = CascadeType.ALL)
    private List<MovieReview> movieReviewlist;

    @OneToMany(mappedBy = "movieGenreMovie", cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenrelist;
}
