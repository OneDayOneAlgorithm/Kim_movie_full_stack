package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.Movie;
import com.example.kim_movie.domain.model.MovieDibs;
import com.example.kim_movie.domain.model.MovieLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieLikeRepository extends JpaRepository<MovieLike,Long> {
    MovieLike findByLikeMemberAndLikeMovie(Member member, Movie movie);
    List<MovieLike> findByLikeMemberAndMovieLikeDone(Member member, Boolean movieLikeDone);
}
