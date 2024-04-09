package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieReview {
    @Id
    @GeneratedValue
    private Long movieReviewId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_review_member_id")
    private Member movieReviewMemberId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_review_movie_id")
    private Movie movieReviewMovieId;

    @Column(nullable = false, length = 100)
    @Setter
    private String content;

    @Setter
    private String createAt;

    @Column(nullable = false)
    @Setter
    private Boolean edit = false;
}
