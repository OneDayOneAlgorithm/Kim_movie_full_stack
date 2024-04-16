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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
                            .movieGenreMovie(movie)
                            .movieGenreGenre(genre)
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
        if (movieDibs == null) {
            movieDibs = MovieDibs.builder()
                    .dibsMember(member)
                    .dibsMovie(movie)
                    .dibsDone(true)
                    .build();
        } else {
            movieDibs.setDibsDone(!movieDibs.getDibsDone());
        }
        movieDibsRepository.save(movieDibs);
        return movieDibs.getDibsDone();
    }

    public Boolean movieLike(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        MovieLike movieLike = movieLikeRepository.findByLikeMemberAndLikeMovie(member, movie);
        if (movieLike == null) {
            movieLike = MovieLike.builder()
                    .likeMember(member)
                    .likeMovie(movie)
                    .movieLikeDone(true)
                    .build();
        } else {
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

    public List<MovieResponse.Detail> retrieveList() {
        return movieRepository.findAll().stream().map(MovieResponse.Detail::of).collect(Collectors.toList());
    }

    public MovieResponse.Detail retrievDetail(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        System.out.println(movie.getMovieTitle());
        return MovieResponse.Detail.of(movie);
    }

    public List<MovieResponse.Detail> retrieveName(String name) {
        List<Movie> movies = movieRepository.findByMovieTitleContaining(name);
        return movies.stream().map(MovieResponse.Detail::of).collect(Collectors.toList());
    }

    public List<MovieResponse.Detail> retrieveRecommend(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Movie> movieLikeList = movieLikeRepository.findMoviesByLikeMemberAndMovieLikeDone(member, true);
        List<Genre> genres = new ArrayList<>();

        for (Movie movie : movieLikeList) {
            List<Genre> movieGenres = movieGenreRepository.findGenresByMovie(movie);
            genres.addAll(movieGenres);
        }

        // 중복된 장르를 제거합니다.
        genres = genres.stream().distinct().collect(Collectors.toList());


        Genre randomGenre = selectRandomGenre(genres);

        // 선택된 장르에 해당하는 영화를 랜덤으로 중복 없이 10개 가져옵니다.
        List<Movie> recommendedMovies = selectRandomMoviesByGenre(randomGenre);

        // 영화 정보를 가공하여 반환합니다.
        return recommendedMovies.stream().map(MovieResponse.Detail::of).collect(Collectors.toList());
    }

    private Genre selectRandomGenre(List<Genre> genres) {
        Random random = new Random();
        return genres.get(random.nextInt(genres.size()));
    }

    private List<Movie> selectRandomMoviesByGenre(Genre genre) {
        List<Movie> moviesByGenre = movieGenreRepository.findMoviesByGenre(genre);
        Random random = new Random();
        int numMoviesToSelect = Math.min(10, moviesByGenre.size());

        List<Movie> selectedMovies = new ArrayList<>();
        while (selectedMovies.size() < numMoviesToSelect && !moviesByGenre.isEmpty()) {
            Movie randomMovie = moviesByGenre.remove(random.nextInt(moviesByGenre.size()));
            selectedMovies.add(randomMovie);
        }

        return selectedMovies;
    }

    public Boolean movieDibsTrue(Long memberId, Long movieId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        MovieDibs movieDibs = movieDibsRepository.findByDibsMemberAndDibsMovie(member, movie);
        if (movieDibs.getDibsDone().equals(true)){
            return true;
        }else{
            return false;
        }
    }
}
