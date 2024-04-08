package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class memberFollowing {
    @Id
    @GeneratedValue
    private Long memberFollowingId;
    @ManyToOne
    @JoinColumn(name="follwer_id")
    private Long fromMemberId;
    private Long toMemberId;
}
