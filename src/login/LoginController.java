package login;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The Login Controller handles the events and the functionality of the Login Scene in the program.
 *
 * @author Ramzy El-Taher
 */
public class LoginController {
  @FXML private PasswordField passwordTF;

  @FXML private TextField usernameTF;

  @FXML private Label errorTextLogin;

  /**
   * The initialize method which sets the error text to false, and sets a default username and
   * password at the start of the program. The default username and password is used for testing and
   * demonstration purposes; in a real program, a default username/password would not be set
   * automatically.
   */
  public void initialize() {
    errorTextLogin.setVisible(false);
    usernameTF.setText("admin");
    passwordTF.setText("password");
  }

  /**
   * Method that handles the events for the Log in button. When the button is pressed, the program
   * checks to see if the user has entered text in the username and password field. If they have not
   * entered any text, an error message would pop up at the bottom, stating that the user needs to
   * enter either a username or password depending on the text fields that are empty. If the text
   * fields both contain valid input, the scene of the program switches to the production line
   * tracker.
   *
   * @param event gets the source, scene, and window of the Production Line Tracker scene. event is
   *     casted to a node, and the entire call gets casted to a Stage.
   * @throws IOException The method has a chance to contain an IOException, so it is added to the
   *     method signature.
   */
  @FXML
  void onActionLogin(ActionEvent event) throws IOException {
    String username = usernameTF.getText();
    String password = passwordTF.getText();
    if ((username.equals(""))) {
      errorTextLogin.setVisible(true);
      errorTextLogin.setText("Please enter a username.");
      usernameTF.requestFocus();
    } else if (password.equals("")) {
      errorTextLogin.setVisible(true);
      errorTextLogin.setText("Please enter a password.");
      passwordTF.requestFocus();
    } else {
      errorTextLogin.setVisible(false);
      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Parent root =
          FXMLLoader.load(getClass().getResource("../productlinetracker/productlinetracker.fxml"));
      primaryStage.setTitle("Production Line Tracker");
      primaryStage.setScene(new Scene(root, 657, 470));
      root.getStylesheets().add("productlinetracker/productlinetracker.css");
      primaryStage.show();
    }
  }
}
