package kosiorek.michal.repository;

import kosiorek.michal.connection.DbConnection;
import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.Movie;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository implements CrudRepository<Movie> {

    private final Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public List<Movie> findAll() {

        try (Statement statement = connection.createStatement()) {

            final String sql = "select id, title, genre, price, duration, release_date from movie";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(resultSet.getString(3))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build());
            }
            return movies;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "MOVIES FIND ALL EXCEPTION");
        }


    }

    @Override
    public Optional<Movie> findById(Integer id) {

        final String sql = "select id, title, genre, price, duration, release_date from movie where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(resultSet.getString(3))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build());
            }
            return Optional.empty();


        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "MOVIES FIND BY ID EXCEPTION");
        }

    }

    @Override
    public void add(Movie movie) {

        if (movie == null) {
            throw new MyException(ExceptionCode.OTHER, "MOVIE OBJECT IS NULL");
        }


        final String sql = "insert into movie ( title, genre, price, duration, release_date ) values (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setBigDecimal(3, movie.getPrice());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "ADD MOVIE EXCEPTION");
        }


    }

    @Override
    public void update(Movie movie) {

        if (movie == null) {
            throw new MyException(ExceptionCode.OTHER, "MOVIE OBJECT IS NULL");
        }

        final String sql = "update movie set title=?,genre=?,price=?, duration=?,release_date=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setBigDecimal(3, movie.getPrice());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
            preparedStatement.setInt(6, movie.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "UPDATE MOVIE EXCEPTION");
        }


    }

    @Override
    public void delete(Integer id) {

        final String sql = "delete from movie where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "DELETE MOVIE EXCEPTION");
        }

    }
}
