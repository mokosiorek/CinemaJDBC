package kosiorek.michal.repository;

import kosiorek.michal.connection.DbConnection;
import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements CrudRepository<Customer> {

    private final Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public List<Customer> findAll() {

        try (Statement statement = connection.createStatement()) {

            final String sql = "select id, name, surname, age, email, loyalty_card_id from customer";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .email(resultSet.getString(5))
                        .loyaltyCardId(resultSet.getInt(6))
                        .build());
            }
            return customers;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMERS FIND ALL EXCEPTION");
        }

    }

    @Override
    public Optional<Customer> findById(Integer id) {

        final String sql = "select id, name, surname, age, email, loyalty_card_id from customer where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .email(resultSet.getString(5))
                        .loyaltyCardId(resultSet.getInt(6))
                        .build());
            }
            return Optional.empty();


        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER FIND BY ID EXCEPTION");
        }

    }

    @Override
    public void add(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }


        //final String sql = "insert into customer ( name, surname, age, email, loyalty_card_id ) values (?,?,?,?,?)";
        final String sql = "insert into customer ( name, surname, age, email ) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setString(4, customer.getEmail());
          //  preparedStatement.setInt(5, customer.getLoyaltyCardId());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.OTHER,"ADD CUSTOMER EXCEPTION");
        }

    }

    @Override
    public void update(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }

        //final String sql = "update customer set name=?,surname=?,age=?, email=?,loyalty_card_id=? where id=?";
        final String sql = "update customer set name=?,surname=?,age=?, email=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setString(4, customer.getEmail());
            //preparedStatement.setInt(5, customer.getLoyaltyCardId());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER,"UPDATE CUSTOMER EXCEPTION");
        }
    }

    @Override
    public void delete(Integer id) {

        final String sql = "delete from customer where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER,"DELETE PLAYER EXCEPTION");
        }

    }

    public Optional<Customer> findByNameSurnameEmail(String name, String surname, String email) {

        final String sql = "select id, name, surname, age, email, loyalty_card_id from customer where name=? AND surname=? AND email=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .email(resultSet.getString(5))
                        .loyaltyCardId(resultSet.getInt(6))
                        .build());
            }
            return Optional.empty();


        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER FIND BY NAME,SURNAME,EMAIL EXCEPTION");
        }

    }

    public void updateWithLoyaltyCard(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }

        final String sql = "update customer set name=?,surname=?,age=?, email=?,loyalty_card_id=? where id=?";
        //final String sql = "update customer set name=?,surname=?,age=?, email=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setInt(5, customer.getLoyaltyCardId());
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER,"UPDATE WITH LOYALTY CARD CUSTOMER EXCEPTION");
        }
    }

}
