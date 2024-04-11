package com.example.kim_movie.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class MovieApiResponse {
    private int page;
    private List<MovieApiItem> results;
    private Long total_pages;
    private Long total_results;

    @Data
    public static class MovieApiItem {
        private boolean adult;
        private String backdrop_path;
        private List<Integer> genre_ids;
        private Long id;
        private String original_language;
        private String original_title;
        private String overview;
        private float popularity;
        private String poster_path;
        private String release_date;
        private String title;
        private Boolean video;
        private float vote_average;
        private Long vote_count;
    }
}