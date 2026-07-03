package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PostgresConnectionUtil - This class extends the ConnectionUtils class and creates a connection to a postgreSQL
 * database.
 * 
 * @author Joshua Brewer
 */
public class PostgresConnectionUtil extends ConnectionUtils {

  static {
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public PostgresConnectionUtil() {
    this.url = System.getenv("DB_URL");
    this.username = System.getenv("DB_USERNAME");
    this.password = System.getenv("DB_PASSWORD");
  }


  @Override
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }
}
