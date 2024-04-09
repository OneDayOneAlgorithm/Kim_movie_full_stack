package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    private Long boardId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_member_id")
    private Member boardMemberId;

    @Column(nullable = false, length = 20)
    @Setter
    private String title;

    @Column(nullable = false, length = 1000)
    @Setter
    private String content;

    @Setter
    private String createAt;

    @Column(nullable = false)
    @Setter
    private Boolean edit = false;
}
