package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.MemberFollowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFollowingRepository extends JpaRepository<MemberFollowing,Long> {
    MemberFollowing findByFromMemberAndToMember(Member fromMember, Member toMember);
}
