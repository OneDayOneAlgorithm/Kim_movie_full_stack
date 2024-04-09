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
public class MovieDibs {
    @Id
    @GeneratedValue
    private Long movieDibsId;

    @ManyToOne(optional=false)
    @JoinColumn(name="dibs_member_id")
    private Member dibsMemberId;

    @ManyToOne(optional=false)
    @JoinColumn(name="dibs_movie_id")
    private Movie dibsMovieId;
}
