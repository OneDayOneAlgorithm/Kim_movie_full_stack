package com.example.kim_movie.controller.response;

import com.example.kim_movie.domain.model.Movie;
import lombok.Builder;
import lombok.Data;

public class MovieResponse {
    @Data
    @Builder
    public static class Detail {
        private Long movieId;
        private String movieTitle;
        private String overview;
        private String releaseDate;
        private String posterPath;
        private Integer likePoint;

        public static Detail of(Movie movie) {
            return Detail.builder()
                    .movieId(movie.getMovieId())
                    .movieTitle(movie.getMovieTitle())
                    .overview(movie.getOverview())
                    .releaseDate(movie.getReleaseDate())
                    .posterPath(movie.getPosterPath())
                    .likePoint(movie.getLikePoint())
                    .build();
        }
    }
}
