package com.example.kim_movie.domain.repository;

import com.example.kim_movie.domain.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewRepository extends JpaRepository<MovieReview,Long> {
}
