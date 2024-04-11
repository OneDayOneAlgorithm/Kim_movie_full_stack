package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.Movie;
import com.example.kim_movie.domain.model.MovieDibs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDibsRepository extends JpaRepository<MovieDibs,Long> {
    MovieDibs findByDibsMemberAndDibsMovie(Member member, Movie movie);
    List<MovieDibs> findByDibsMemberAndDibsDone(Member member, Boolean dibsDone);
}
