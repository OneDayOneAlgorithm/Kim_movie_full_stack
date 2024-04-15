package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Genre;
import com.example.kim_movie.domain.model.Movie;
import com.example.kim_movie.domain.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieGenreRepository extends JpaRepository<MovieGenre,Long> {
    @Query("SELECT mg.movieGenreGenre FROM MovieGenre mg WHERE mg.movieGenreMovie = :movie")
    List<Genre> findGenresByMovie(@Param("movie") Movie movie);

    @Query("SELECT mg.movieGenreMovie FROM MovieGenre mg WHERE mg.movieGenreGenre = :genre")
    List<Movie> findMoviesByGenre(@Param("genre") Genre genre);

}
