package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Member dibsMember;

    @ManyToOne(optional=false)
    @JoinColumn(name="dibs_movie_id")
    private Movie dibsMovie;

    @Column(nullable = false)
    @Setter
    private Boolean dibsDone;
}
