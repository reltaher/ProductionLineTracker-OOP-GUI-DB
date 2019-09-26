package prodLineTracker;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** @author Ramzy El-Taher */
public class Main extends Application {

  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage.
   * @throws Exception Any problem with the code.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(root, 657, 470));
    root.getStylesheets().add("prodLineTracker/prodLineTracker.css");
    primaryStage.show();
  }

  /**
   * The initializeDB method connects to the H2 Database made within the IDE. Once connected, a
   * statement is created and a SQL command is executed. If the query is successfully executed, a
   * message will state that the product record has been inserted into the datatbase.
   *
   * @return nothing
   */
  void initializeDB() {
    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/ProductLineDB";

    //  Database credentials

    final String USER = "";

    final String PASS = "";

    Connection conn;
    try {
      // STEP 1: Register JDBC driver

      Class.forName(JDBC_DRIVER);

      // STEP 2: Open a connection
      System.out.println("Connecting to Database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Successfully connected to Database.");
      // STEP 3: Execute a query
      System.out.println("Inserting Product records into table...");
      Statement stmt = conn.createStatement();
        String sql =
            "INSERT INTO Product(type, manufacturer, name) " + "VALUES ( 'AUDIO', 'Apple', 'iPod' )";
        stmt.executeUpdate(sql);
        System.out.println("Product record has been inserted successfully.");
    } catch (SQLException e) {
      System.out.println("Error: SQL Exception.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Error: Class Not Found.");
      e.printStackTrace();

    }
  }

  /**
   * The main method, which will launch the args parameter, allowing the program to start.
   * @param args String variable which contains everything.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
