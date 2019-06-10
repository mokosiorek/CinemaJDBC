package kosiorek.michal.service;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.Movie;
import kosiorek.michal.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private MovieRepository movieRepository = new MovieRepository();

    public void addMovie(Movie movie) {

        movieRepository.add(movie);

    }

    public void addMovieList(List<Movie> movies) {
        movies.forEach(movie -> movieRepository.add(movie));
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(Movie movie){
        movieRepository.delete(movie.getId());
    }

    public void editMovie(Movie movie){
        movieRepository.update(movie);
    }

    public Movie getMovieById(int id){
        return movieRepository.findById(id).orElseThrow(() ->new MyException(ExceptionCode.OTHER,"Movie not found"));
    }
}
