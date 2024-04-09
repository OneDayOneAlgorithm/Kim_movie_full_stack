package com.example.kim_movie.controller;

import com.example.kim_movie.application.MovieAppService;
import com.example.kim_movie.controller.request.MovieRequest;
import com.example.kim_movie.controller.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieAppService movieAppService;

//    @PostMapping
//    public MovieResponse.Detail createMovie(@RequestBody MovieRequest.Create request){
//        return movieAppService.createmovie(request);
//    }
//
//    @PutMapping("{id}")
//    public MovieResponse.Detail updateMovie(@PathVariable Long id, @RequestBody MovieRequest.Update request){
//        return movieAppService.updatemovie(request);
//    }
//
//    @GetMapping("/{id}")
//    public MovieResponse.Detail retrieveDetail(Long id){
//        return movieAppService.retrievDetail(id);
//    }
//
//    @GetMapping
//    public List<MovieResponse.ListElem> retrieveDetail(){
//        return movieAppService.retrieveList();
//    }
    @GetMapping("/{num}")
    public Boolean saveMovieByTMDB(@PathVariable Long num) throws IOException {
        return movieAppService.saveMovieByTMDB(num);
    }
}
