package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardComment {
    @Id
    @GeneratedValue
    private Long boardCommentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_comment_board_id")
    private Board boardCommentBoardId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_comment_member_id")
    private Member boardCommentMemberId;

    @Column(nullable = false, length = 100)
    @Setter
    private String content;

    @Setter
    private String createAt;

    @Column(nullable = false)
    @Setter
    private Boolean edit;
}
