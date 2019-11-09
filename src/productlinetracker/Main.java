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
 *
 *     The program is a software made in JavaFX where it tracks the number and
 *     types of products being made. This file is the Main class, where the program is launched and
 *     executed. The program is checked with CheckStyle using Google Checks, which uses the p tag.
 *
 *     Date: 09/26/19
 */
public class Main extends Application {

  /**
   * Method that starts the JavaFX program.
   *
   * @brief starts JavaFX program.
   *     The start method is the starting point of a JavaFX program. This start method sets the
   *     title.
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
   * Starts program.
   *
   * <p>The main method, which will launch the args parameter, allowing the program to start.
   *
   * @param args String variable which contains everything.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
