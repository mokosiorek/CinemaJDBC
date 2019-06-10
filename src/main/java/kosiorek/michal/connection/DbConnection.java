package kosiorek.michal.connection;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private DbConnection() {
        connect();
        createTables();
    }


    private final static String DRIVER = "org.sqlite.JDBC";
    private final static String DATABASE = "jdbc:sqlite:test.db";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private void connect() {
        try {
            Class.forName(DRIVER);

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            connection = DriverManager.getConnection(DATABASE, config.toProperties());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.OTHER, "DB CONNECT FAILED");
        }
    }

    private void createTables() {
        try (Statement statement = connection.createStatement()) {

            final String moviesSql = SqlCommand.builder()
                    .table("movie")
                    .primaryKey("id")
                    .stringColumn("title", 50, "not null")
                    .stringColumn("genre", 50, "not null")
                    .decimalColumn("price", 4, 2, "not null")
                    .intColumn("duration", "not null")
                    .column("release_date", "Date", "not null")
                    .build()
                    .toSql();

            final String loyaltyCardSql = SqlCommand.builder()
                    .table("loyalty_card")
                    .primaryKey("id")
                    .column("expiration_date", "Date", "not null")
                    .decimalColumn("discount", 2, 0, "not null")
                    .intColumn("movies_number", "not null")
                    .build()
                    .toSql();

            final String customersSql = SqlCommand.builder()
                    .table("customer")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .stringColumn("surname", 50, "not null")
                    .intColumn("age", "not null")
                    .stringColumn("email", 50, "not null")
                    .intColumn("loyalty_card_id","")
                    .foreignKey("loyalty_card_id", "loyalty_card", "id", "on delete cascade on update cascade")
                    .build()
                    .toSql();


            final String salesStandSql = SqlCommand.builder()
                    .table("sales_stand")
                    .primaryKey("id")
                    .intColumn("customer_id","not null")
                    .foreignKey("customer_id","customer","id","on delete cascade on update cascade")
                    .intColumn("movie_id","not null")
                    .foreignKey("movie_id","movie","id","on delete cascade on update cascade")
                    .column("start_date_time","timestamp","not null")
                    .build()
                    .toSql();


            statement.execute(moviesSql);
            statement.execute(loyaltyCardSql);
            statement.execute(customersSql);
            statement.execute(salesStandSql);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }
    }
}
