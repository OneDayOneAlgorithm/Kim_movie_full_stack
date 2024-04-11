package com.example.kim_movie.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Boolean isStaff;

    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL)
    private List<MemberFollowing> followingList;

    @OneToMany(mappedBy = "toMember", cascade = CascadeType.ALL)
    private List<MemberFollowing> followersList;

    @OneToMany(mappedBy = "dibsMember", cascade = CascadeType.ALL)
    private List<MovieDibs> movieDibsList;

    @OneToMany(mappedBy = "likeMember", cascade = CascadeType.ALL)
    private List<MovieLike> movieLikeList;

    @OneToMany(mappedBy = "movieReviewMember", cascade = CascadeType.ALL)
    private List<MovieReview> movieReviewList;

}
