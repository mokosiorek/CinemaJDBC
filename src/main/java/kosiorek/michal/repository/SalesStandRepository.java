package kosiorek.michal.repository;

import kosiorek.michal.connection.DbConnection;
import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.LoyaltyCard;
import kosiorek.michal.model.SalesStand;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalesStandRepository implements CrudRepository<SalesStand> {

    private final Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public List<SalesStand> findAll() {

        try (Statement statement = connection.createStatement()) {

            final String sql = "select id, customer_id, movie_id, start_date_time from sales_stand";
            ResultSet resultSet = statement.executeQuery(sql);
            List<SalesStand> salesStands = new ArrayList<>();
            while (resultSet.next()) {
                salesStands.add(SalesStand.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDateTime(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return salesStands;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND FIND ALL EXCEPTION");
        }



    }

    @Override
    public Optional<SalesStand> findById(Integer id) {

        final String sql = "select id, customer_id,movie_id,start_date_time from sales_stand where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(SalesStand.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDateTime(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return Optional.empty();


        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND FIND BY ID EXCEPTION");
        }

    }

    @Override
    public void add(SalesStand salesStand) {

        if (salesStand == null) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND OBJECT IS NULL");
        }

        final String sql = "insert into sales_stand (start_date_time, movie_id, customer_id) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(salesStand.getStartDateTime()));
            preparedStatement.setInt(2, salesStand.getMovieId());
            preparedStatement.setInt(3, salesStand.getCustomerId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "ADD SALES STAND EXCEPTION");
        }

    }

    @Override
    public void update(SalesStand salesStand) {

        if (salesStand == null) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND OBJECT IS NULL");
        }

        final String sql = "update sales_stand set customer_id=?, movie_id=?, start_date_time=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, salesStand.getCustomerId());
            preparedStatement.setInt(2, salesStand.getMovieId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(salesStand.getStartDateTime()));
            preparedStatement.setInt(4, salesStand.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "UPDATE SALES STAND EXCEPTION");
        }

    }

    @Override
    public void delete(Integer id) {

        final String sql = "delete from sales_stand where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "DELETE SALES STAND EXCEPTION");
        }

    }
}
