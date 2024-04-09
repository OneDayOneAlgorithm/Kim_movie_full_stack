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
public class BoardLike {
    @Id
    @GeneratedValue
    private Long boardLikeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_like_board_id")
    private Board boardLikeBoardId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_like_member_id")
    private Member boardLikeMemberId;
}
