package kosiorek.michal.service;

import kosiorek.michal.converters.MoviesJsonConverter;
import kosiorek.michal.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderService {

    private Scanner sc = new Scanner(System.in);
    UserDataService userDataService = new UserDataService();

    public List<Movie> readMoviesFromFile() {

        String filename = userDataService.getString("Enter filename: ","[A-Za-z0-9._ ]+");

        List<Movie> movies = new ArrayList<>();

        new MoviesJsonConverter(filename).fromJson().ifPresent(moviesJson -> movies.addAll(moviesJson.getMovies()));

        return movies;
    }

}
