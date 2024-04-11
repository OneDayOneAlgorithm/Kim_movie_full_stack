package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre findByGenreNum(Integer genreId);
}
