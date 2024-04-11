package com.example.kim_movie.application;

import com.example.kim_movie.controller.request.MovieReviewRequest;
import com.example.kim_movie.controller.response.*;
import com.example.kim_movie.domain.model.*;
import com.example.kim_movie.domain.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovieAppService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MemberRepository memberRepository;
    private final MovieDibsRepository movieDibsRepository;
    private final MovieLikeRepository movieLikeRepository;
    private final MovieReviewRepository movieReviewRepository;

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    public Boolean saveMovieByTMDB(Long num) throws IOException {

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?language=ko-KR&page=" + num)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjQ2ZmI5OWY4ODEzOGY4NmZjNmM3NjdlYmU5NTllYyIsInN1YiI6IjYzZDMxNzgyMDMxYTFkMDBjMDk1NDk2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Tfu_j4_8AgzwIl7Z-ZfVcrqSiiQ24OxUifBehQ3n5BU")
                .build();

        Response response = client.newCall(request).execute();
        MovieApiResponse apiResponse = objectMapper.readValue(response.body().string(), MovieApiResponse.class);
        List<MovieApiResponse.MovieApiItem> movieApiItems = apiResponse.getResults();

        for (MovieApiResponse.MovieApiItem movieApiItem : movieApiItems) {
            if (movieApiItem.getOverview().equals("")) {
                continue;
            }
            Movie movie = new Movie();
            movie.setMovieTitle(movieApiItem.getTitle());
            movie.setOverview(movieApiItem.getOverview());
            movie.setPosterPath("https://image.tmdb.org/t/p/w500" + movieApiItem.getPoster_path());
            movie.setReleaseDate(movieApiItem.getRelease_date());

            movieRepository.save(movie);

            MovieGenre movieGenre = new MovieGenre();

            List<Integer> genreIds = movieApiItem.getGenre_ids();
            for (Integer genreId : genreIds) {
                Genre genre = genreRepository.findByGenreNum(genreId);
                if (genre != null) {
                    movieGenre = MovieGenre.builder()
                            .movieGenreMovieId(movie)
                            .movieGenreGenreId(genre)
                            .build();

                    movieGenreRepository.save(movieGenre);
                }
            }
        }
        return true;
    }

    public Boolean saveGenre() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/genre/movie/list?language=ko")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjQ2ZmI5OWY4ODEzOGY4NmZjNmM3NjdlYmU5NTllYyIsInN1YiI6IjYzZDMxNzgyMDMxYTFkMDBjMDk1NDk2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Tfu_j4_8AgzwIl7Z-ZfVcrqSiiQ24OxUifBehQ3n5BU")
                .build();

        Response response = client.newCall(request).execute();

        GenreApiResponse apiResponse = objectMapper.readValue(response.body().string(), GenreApiResponse.class);
        System.out.println(apiResponse);
        List<GenreApiResponse.GenreApiItem> genreApiItems = apiResponse.getGenres();
        for (GenreApiResponse.GenreApiItem genreApiItem : genreApiItems) {
            Genre genre = new Genre();
            genre.setGenreNum(genreApiItem.getId());
            genre.setGenreName(genreApiItem.getName());

            genreRepository.save(genre);
        }
        return true;
    }

    public Boolean movieDibs(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        MovieDibs movieDibs = movieDibsRepository.findByDibsMemberAndDibsMovie(member, movie);
        if(movieDibs == null) {
            movieDibs = MovieDibs.builder()
                    .dibsMember(member)
                    .dibsMovie(movie)
                    .dibsDone(true)
                    .build();
        }else{
            movieDibs.setDibsDone(!movieDibs.getDibsDone());
        }
        movieDibsRepository.save(movieDibs);
        return movieDibs.getDibsDone();
    }

    public Boolean movieLike(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        MovieLike movieLike = movieLikeRepository.findByLikeMemberAndLikeMovie(member, movie);
        if(movieLike == null) {
            movieLike = MovieLike.builder()
                    .likeMember(member)
                    .likeMovie(movie)
                    .movieLikeDone(true)
                    .build();
        }else{
            movieLike.setMovieLikeDone(!movieLike.getMovieLikeDone());
        }
        movieLikeRepository.save(movieLike);
        return movieLike.getMovieLikeDone();
    }

    public MovieReviewResponse.Detail createMovieReview(MovieReviewRequest.Create request) {
        Member member = memberRepository.findById(request.getMovieReviewMemberId()).orElseThrow();
        Movie movie = movieRepository.findById(request.getMovieReviewMovieId()).orElseThrow();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        MovieReview movieReview = MovieReview.builder()
                .movieReviewMovie(movie)
                .movieReviewMember(member)
                .content(request.getContent())
                .createAt(formattedDateTime)
                .edit(false)
                .build();

        movieReviewRepository.save(movieReview);
        return MovieReviewResponse.Detail.of(movieReview);
    }

    public MovieReviewResponse.Detail updateMovieReview(MovieReviewRequest.Update request) {
        MovieReview movieReview = movieReviewRepository.findById(request.getMovieReviewId()).orElseThrow();
        movieReview.setContent(request.getContent());
        movieReview.setEdit(true);
        movieReviewRepository.save(movieReview);
        return MovieReviewResponse.Detail.of(movieReview);
    }

    public Boolean deleteMovieReview(Long movieReviewId) {
        MovieReview movieReview = movieReviewRepository.findById(movieReviewId).orElseThrow();
        movieReviewRepository.delete(movieReview);
        return true;
    }

    public MovieReviewResponse.Detail getMovieReview(Long movieReviewId) {
        MovieReview movieReview = movieReviewRepository.findById(movieReviewId).orElseThrow();
        return MovieReviewResponse.Detail.of(movieReview);
    }

    public List<MovieReviewResponse.Detail> getAllMovieReview() {
        return movieReviewRepository.findAll().stream()
                .map(MovieReviewResponse.Detail::of)
                .toList();
    }

    public List<MovieResponse.Detail> movieDibsList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<MovieDibs> movieDibsList = movieDibsRepository.findByDibsMemberAndDibsDone(member, true);

        List<Long> dibsMovieIds = movieDibsList.stream()
                .map(dibs -> dibs.getDibsMovie().getMovieId()) // 찜한 영화 목록에서 영화 ID만 추출
                .collect(Collectors.toList());

        List<Movie> dibsMovies = movieRepository.findAllById(dibsMovieIds); // 영화 ID를 사용하여 영화 목록을 조회
        return dibsMovies.stream()
                .map(MovieResponse.Detail::of) // 영화 목록을 MovieResponse 형식으로 변환
                .collect(Collectors.toList());
    }

    public List<MovieResponse.Detail> movieLikeList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<MovieLike> movieLikeList = movieLikeRepository.findByLikeMemberAndMovieLikeDone(member, true);

        List<Long> likeMovieIds = movieLikeList.stream()
                .map(dibs -> dibs.getLikeMovie().getMovieId()) // 찜한 영화 목록에서 영화 ID만 추출
                .collect(Collectors.toList());

        List<Movie> likeMovies = movieRepository.findAllById(likeMovieIds); // 영화 ID를 사용하여 영화 목록을 조회
        return likeMovies.stream()
                .map(MovieResponse.Detail::of) // 영화 목록을 MovieResponse 형식으로 변환
                .collect(Collectors.toList());
    }
}
