package com.example.kim_movie.controller;

import com.example.kim_movie.application.MovieAppService;
import com.example.kim_movie.controller.request.MovieReviewRequest;
import com.example.kim_movie.controller.response.MovieResponse;
import com.example.kim_movie.controller.response.MovieReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieAppService movieAppService;

    @GetMapping("/{id}")
    public MovieResponse.Detail retrieveDetail(@PathVariable Long id){
        return movieAppService.retrievDetail(id);
    }

    @GetMapping("/name/{name}")
    public List<MovieResponse.Detail> retrieveDetail(@PathVariable String name){
        return movieAppService.retrieveName(name);
    }

    @GetMapping
    public List<MovieResponse.Detail> retrieveList(){
        return movieAppService.retrieveList();
    }

    @GetMapping("/recommend/{memberId}")
    public List<MovieResponse.Detail> retrieveRecommend(@PathVariable Long memberId){
        return movieAppService.retrieveRecommend(memberId);
    }

    @GetMapping("/save/{num}")
    public Boolean saveMovieByTMDB(@PathVariable Long num) throws IOException {
        return movieAppService.saveMovieByTMDB(num);
    }

    @GetMapping("/genre")
    public Boolean saveGenre() throws IOException {
        return movieAppService.saveGenre();
    }

    @GetMapping("/dibs/{memberId}/{movieId}")
    public Boolean movieDibs(@PathVariable Long memberId, @PathVariable Long movieId){
        return movieAppService.movieDibs(memberId, movieId);
    }

    @GetMapping("/dibs/true/{memberId}/{movieId}")
    public Boolean movieDibsTrue(@PathVariable Long memberId, @PathVariable Long movieId){
        return movieAppService.movieDibsTrue(memberId, movieId);
    }

    @GetMapping("/dibs/{memberId}")
    public List<MovieResponse.Detail> movieDibsList(@PathVariable Long memberId){
        return movieAppService.movieDibsList(memberId);
    }

    @GetMapping("/like/{memberId}/{movieId}")
    public Boolean movieLike(@PathVariable Long memberId, @PathVariable Long movieId){
        return movieAppService.movieLike(memberId, movieId);
    }

    @GetMapping("/like/{memberId}")
    public List<MovieResponse.Detail> movieLikeList(@PathVariable Long memberId){
        return movieAppService.movieLikeList(memberId);
    }

    @PostMapping("/movieReview")
    public MovieReviewResponse.Detail createMovieReview(@RequestBody MovieReviewRequest.Create request){
        return movieAppService.createMovieReview(request);
    }

    @PutMapping("/movieReview")
    public MovieReviewResponse.Detail updateMovieReview(@RequestBody MovieReviewRequest.Update request){
        return movieAppService.updateMovieReview(request);
    }

    @DeleteMapping("/movieReview/{movieReviewId}")
    public Boolean deleteMovieReview(@PathVariable Long movieReviewId){
        return movieAppService.deleteMovieReview(movieReviewId);
    }

    @GetMapping("/movieReview/{movieReviewId}")
    public MovieReviewResponse.Detail getMovieReview(@PathVariable Long movieReviewId){
        return movieAppService.getMovieReview(movieReviewId);
    }

    @GetMapping("/movieReview")
    public List<MovieReviewResponse.Detail> getAllMovieReview(){
        return movieAppService.getAllMovieReview();
    }

}
