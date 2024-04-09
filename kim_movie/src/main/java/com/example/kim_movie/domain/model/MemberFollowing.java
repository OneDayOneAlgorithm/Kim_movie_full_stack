package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFollowing {
    @Id
    @GeneratedValue
    private Long memberFollowingId;

    @ManyToOne(optional=false)
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @ManyToOne(optional=false)
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    @Column(nullable = false)
    @Setter
    private Boolean following = true;
}
