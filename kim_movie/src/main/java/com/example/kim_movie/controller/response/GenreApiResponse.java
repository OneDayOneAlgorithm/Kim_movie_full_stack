package com.example.kim_movie.controller.response;

import com.example.kim_movie.domain.model.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GenreApiResponse {
    private List<GenreApiItem> genres;
    @Data
    public static class GenreApiItem {
        private Long id;
        private String name;
    }
}
