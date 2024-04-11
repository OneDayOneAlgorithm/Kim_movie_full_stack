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

    @ManyToOne
    @JoinColumn(name = "movie_review_member_id", referencedColumnName = "memberId")
    private Member movieReviewMember;

    @ManyToOne
    @JoinColumn(name = "movie_review_movie_id", referencedColumnName = "movieId")
    private Movie movieReviewMovie;

    @Column(nullable = false, length = 100)
    @Setter
    private String content;

    @Setter
    private String createAt;

    @Column(nullable = false)
    @Setter
    private Boolean edit;
}
