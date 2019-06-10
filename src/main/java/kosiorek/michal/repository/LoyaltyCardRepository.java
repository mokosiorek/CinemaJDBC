package kosiorek.michal.repository;

import kosiorek.michal.connection.DbConnection;
import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.LoyaltyCard;
import kosiorek.michal.model.Movie;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoyaltyCardRepository implements CrudRepository<LoyaltyCard> {

    private final Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public List<LoyaltyCard> findAll() {

        try (Statement statement = connection.createStatement()) {

            final String sql = "select id, expiration_date, discount, movies_number from loyalty_card";
            ResultSet resultSet = statement.executeQuery(sql);
            List<LoyaltyCard> loyaltyCards = new ArrayList<>();
            while (resultSet.next()) {
                loyaltyCards.add(LoyaltyCard.builder()
                        .id(resultSet.getInt(1))
                        .expirationDate(resultSet.getDate(2).toLocalDate())
                        .discount(resultSet.getBigDecimal(3))
                        .moviesNumber(resultSet.getInt(4))
                        .build());
            }
            return loyaltyCards;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARDS FIND ALL EXCEPTION");
        }


    }

    @Override
    public Optional<LoyaltyCard> findById(Integer id) {

        final String sql = "select id, expiration_date, discount, movies_number from loyalty_card where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(LoyaltyCard.builder()
                        .id(resultSet.getInt(1))
                        .expirationDate(resultSet.getDate(2).toLocalDate())
                        .discount(resultSet.getBigDecimal(3))
                        .moviesNumber(resultSet.getInt(4))
                        .build());
            }
            return Optional.empty();


        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARDS FIND BY ID EXCEPTION");
        }

    }

    @Override
    public void add(LoyaltyCard loyaltyCard) {

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }


        final String sql = "insert into loyalty_card ( movies_number, discount, expiration_date ) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, loyaltyCard.getMoviesNumber());
            preparedStatement.setBigDecimal(2, loyaltyCard.getDiscount());
            preparedStatement.setDate(3, Date.valueOf(loyaltyCard.getExpirationDate()));
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "ADD LOYALTY CARD EXCEPTION");
        }


    }

    @Override
    public void update(LoyaltyCard loyaltyCard) {

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }

        final String sql = "update loyalty_card set movies_number=?,discount=?,expiration_date=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, loyaltyCard.getMoviesNumber());
            preparedStatement.setBigDecimal(2, loyaltyCard.getDiscount());
            preparedStatement.setDate(3, Date.valueOf(loyaltyCard.getExpirationDate()));
            preparedStatement.setInt(4, loyaltyCard.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "UPDATE LOYALTY CARD EXCEPTION");
        }


    }

    @Override
    public void delete(Integer id) {

        final String sql = "delete from loyalty_card where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "DELETE LOYALTY CARD EXCEPTION");
        }

    }


    public int addAndReturnId(LoyaltyCard loyaltyCard) {

        int id = -1;

        if (loyaltyCard == null) {
            throw new MyException(ExceptionCode.OTHER, "LOYALTY CARD OBJECT IS NULL");
        }


        final String sql = "insert into loyalty_card ( movies_number, discount, expiration_date ) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, loyaltyCard.getMoviesNumber());
            preparedStatement.setBigDecimal(2, loyaltyCard.getDiscount());
            preparedStatement.setDate(3, Date.valueOf(loyaltyCard.getExpirationDate()));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "ADD LOYALTY CARD EXCEPTION");
        }

        return id;

    }

}
