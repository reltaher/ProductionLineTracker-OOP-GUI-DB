package productlinetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

  private static Database instance = null;
  private final String DB_URL;
  private final String USER;
  private final String PASS;

  public Database() {
    DB_URL = "jdbc:h2:./res/ProductLineDB";
    USER = "";
    PASS = reverseDatabasePassword("wpbd");
  }

  static {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return connection;
  }

  /**
   * Method that reverses the order of the text stored for the database password.
   *
   * @param password The String variable that contains the password.
   * @return reversedPw, which is the reversed password obtained from the method.
   */
  public static String reverseDatabasePassword(String password) {
    if (password.length() <= 0) {
      return password;
    }
    return reverseDatabasePassword(password.substring(1)) + password.charAt(0);
  }

}
