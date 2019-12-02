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

public class LoginController {
  @FXML private PasswordField passwordTF;

  @FXML private TextField usernameTF;

  @FXML
  private Label errorTextLogin;

  public void initialize() {
    errorTextLogin.setVisible(false);
  }

  @FXML
  void onActionLogin(ActionEvent event) throws IOException {
    String username = usernameTF.getText();
    String password = passwordTF.getText();
    if ((username.equals(""))) {
      errorTextLogin.setVisible(true);
      errorTextLogin.setText("Please enter a username.");
    } else if (password.equals("")) {
      errorTextLogin.setVisible(true);
      errorTextLogin.setText("Please enter a password.");
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
