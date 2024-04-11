package com.example.kim_movie.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DibsResponse {
    private MovieResponse.Detail movie;
}
