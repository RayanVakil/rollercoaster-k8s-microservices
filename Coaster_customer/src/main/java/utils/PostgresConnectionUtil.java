package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(PostgresConnectionUtil.class);

  static {
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
    } catch (SQLException throwables) {
      logger.error("Exception occurred", throwables);
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
