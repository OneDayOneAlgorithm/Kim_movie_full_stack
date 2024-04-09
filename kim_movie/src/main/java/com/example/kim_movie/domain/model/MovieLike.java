package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieLike {
    @Id
    @GeneratedValue
    private Long movieLikeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "like_member_id")
    private Member likeMemberId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "like_movie_id")
    private Movie likeMovieId;
}
