package com.example.kim_movie.controller.request;

import com.example.kim_movie.domain.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MovieRequest {
    @Data
    public static class TMDBResponse{
        private int page;
        private List<Movie> results;
    }
}
