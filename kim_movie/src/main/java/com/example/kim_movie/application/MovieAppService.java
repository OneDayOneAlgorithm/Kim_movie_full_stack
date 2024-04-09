package com.example.kim_movie.application;

import com.example.kim_movie.domain.model.Movie;
import com.example.kim_movie.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class MovieAppService {
    private final MovieRepository movieRepository;
    public Boolean saveMovieByTMDB(Long num) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?language=ko-KR&page=" + num)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjQ2ZmI5OWY4ODEzOGY4NmZjNmM3NjdlYmU5NTllYyIsInN1YiI6IjYzZDMxNzgyMDMxYTFkMDBjMDk1NDk2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Tfu_j4_8AgzwIl7Z-ZfVcrqSiiQ24OxUifBehQ3n5BU")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        for (response.body().string().results)
//        Movie movie = Movie.builder()
//                .movieTitle()
        return true;
    }
}
