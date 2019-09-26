package sample;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Statement;

/**
 * @author Ramzy El-Taher
 */
public class Main extends Application {

  private Statement stmt;
  private ObservableList<String> list;

    /**
     * The start method is the starting point of a JavaFX program. This start method sets the title.
     * @brief
     * @param primaryStage The primary stage.
     * @throws Exception Any problem with the code.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeDB();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Production Line Tracker");
        primaryStage.setScene(new Scene(root,657, 470));
        primaryStage.show();
    }

    public void initializeDB() {
      final String JDBC_DRIVER = "org.h2.Driver";

      final String DB_URL = "jdbc:h2:./res/ProductLineDB";


      //  Database credentials

      final String USER = "";

      final String PASS = "";

      Connection conn = null;
      try {
        // STEP 1: Register JDBC driver

        Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection

        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        //STEP 3: Execute a query

        stmt = conn.createStatement();
        //String sql = "INSERT INTO Product(type, manufacturer, name) "
        //    + "VALUES ( 'AUDIO', 'Apple', 'iPod' )";
        String sql = "INSERT INTO Product(type, manufacturer, name) "
            + "VALUES ( 'AUDIO', 'Apple', 'iPod' )";

        int rs = stmt.executeUpdate(sql);

      } catch (SQLException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
