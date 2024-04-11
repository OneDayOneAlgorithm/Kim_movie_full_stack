package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieLike {
    @Id
    @GeneratedValue
    private Long movieLikeId;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "like_member_id")
    private Member likeMember;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "like_movie_id")
    private Movie likeMovie;

    @Column(nullable = false)
    @Setter
    private Boolean movieLikeDone;
}
