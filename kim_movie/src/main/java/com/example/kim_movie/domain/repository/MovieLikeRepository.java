package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.Movie;
import com.example.kim_movie.domain.model.MovieDibs;
import com.example.kim_movie.domain.model.MovieLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieLikeRepository extends JpaRepository<MovieLike,Long> {
    MovieLike findByLikeMemberAndLikeMovie(Member member, Movie movie);
    List<MovieLike> findByLikeMemberAndMovieLikeDone(Member member, Boolean movieLikeDone);
    @Query("SELECT ml.likeMovie FROM MovieLike ml WHERE ml.likeMember = :member AND ml.movieLikeDone = :movieLikeDone")
    List<Movie> findMoviesByLikeMemberAndMovieLikeDone(@Param("member") Member member, @Param("movieLikeDone") Boolean movieLikeDone);

}
