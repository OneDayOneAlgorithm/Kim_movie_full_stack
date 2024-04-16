package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmailAndPassword(String email, String password);
}

