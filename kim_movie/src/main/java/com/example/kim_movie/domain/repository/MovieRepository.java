package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
