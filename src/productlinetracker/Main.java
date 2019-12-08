package productlinetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The program is a software made in JavaFX where it tracks the number and types of products being
 * made. This file is the Main class, where the program is launched and executed. The program is
 * checked with CheckStyle using Google Checks, which uses the p tag.
 *
 * <p>Date: 09/26/19
 *
 * @author Ramzy El-Taher
 */
public class Main extends Application {

  /**
   * Method that starts the JavaFX program.
   *
   * @param primaryStage The primary stage, which sets the title, scene, and ability to show the
   *                     program.
   * @throws Exception Any problem with the code.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(root, 657, 470));
    root.getStylesheets().add("login/login.css");
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
