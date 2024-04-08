package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class member {
    @Id
    @GeneratedValue
    private Long memberId;
    @Column(unique = true)
    private String email;
    @Setter
    private String password;
    @Setter
    private String username;
    private String date_joined;
    private Boolean isStaff;

}
