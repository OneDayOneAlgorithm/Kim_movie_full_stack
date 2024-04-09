package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    @Setter
    private String password;

    @Column(nullable = false, length = 20)
    @Setter
    private String username;

    @Setter
    private String dateJoined;

    @Column(nullable = false)
    @Setter
    private Boolean isStaff = false;

}
