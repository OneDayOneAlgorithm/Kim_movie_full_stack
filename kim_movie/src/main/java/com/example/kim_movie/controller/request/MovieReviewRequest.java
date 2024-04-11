package com.example.kim_movie.controller.request;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.Movie;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Setter;

public class MovieReviewRequest {
    @Data
    public static class Create {
        private Long movieReviewMemberId;
        private Long movieReviewMovieId;
        private String content;
    }

    @Data
    public static class Update {
        private Long movieReviewId;
        private String content;
    }
}
