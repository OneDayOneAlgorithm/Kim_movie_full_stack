package com.example.kim_movie.controller.response;

import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.MovieReview;
import lombok.Builder;
import lombok.Data;

public class MovieReviewResponse {
    @Data
    @Builder
    public static class Detail {
        private Long movieReviewId;
        private MemberResponse.Detail member;
        private MovieResponse.Detail movie;
        private String content;
        private String createAt;
        private Boolean edit;

        public static MovieReviewResponse.Detail of(MovieReview movieReview){
            return MovieReviewResponse.Detail.builder()
                    .movieReviewId(movieReview.getMovieReviewId())
                    .member(MemberResponse.Detail.of(movieReview.getMovieReviewMember()))
                    .movie(MovieResponse.Detail.of(movieReview.getMovieReviewMovie()))
                    .content(movieReview.getContent())
                    .createAt(movieReview.getCreateAt())
                    .edit(movieReview.getEdit())
                    .build();
        }
    }
}
