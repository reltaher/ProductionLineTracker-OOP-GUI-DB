package productlinetracker;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed.
 *     <p>Date: 09/26/19
 */
public class Main extends Application {

  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage, which sets the title, scene, and ability to show the
   *     program.
   * @throws Exception Any problem with the code.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("productlinetracker.fxml"));
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(root, 657, 470));
    root.getStylesheets().add("productlinetracker/productlinetracker.css");
    primaryStage.show();

  }

  /**
   * @brief connects to H2 database and executes a query.
   *     <p>The initializeDB method connects to the H2 Database made within the IDE. Once connected,
   *     a statement is created and a SQL command is executed. If the query is successfully
   *     executed, a message will state that the product record has been inserted into the database.
   * @return nothing
   */
  void initializeDB() {
    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/ProductLineDB";

    //  Database credentials (Username/Password are temporary)
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
      // Statement used to execute queries
      Statement stmt = conn.createStatement();
      // SQL query stored in a string
      String sql =
          "INSERT INTO Product(type, manufacturer, name) " + "VALUES ( 'AUDIO', 'Apple', 'iPod' )";
      // Executes a SQL database query
      stmt.executeUpdate(sql);
      // If execution was successful, this message will print.
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
   * @brief starts program.
   *     <p>The main method, which will launch the args parameter, allowing the program to start.
   * @param args String variable which contains everything.
   * @return nothing
   */
  public static void main(String[] args) {
    launch(args);
  }
}
