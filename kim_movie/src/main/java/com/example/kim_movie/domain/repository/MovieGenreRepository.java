package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre,Long> {
}
