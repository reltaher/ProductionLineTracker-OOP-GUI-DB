package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  private TextField prodNameTA;

  @FXML
  private TextField manufacturerTA;

  @FXML
  private ChoiceBox<?> itemTypeCbox;

  @FXML
  private Button addProduct;

  @FXML
  private TableView<?> tableView;

  @FXML
  private ComboBox<Integer> prodAmtCbox;
  
  @FXML
  private Button recBtn;


  @FXML
  void handleEventAddProduct(ActionEvent event) {

  }

  @FXML
  void handleEventRecordProduction(ActionEvent event) {

  }

  public void initialize() {
    prodAmtCbox.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
    prodAmtCbox.setEditable(true);
    prodAmtCbox.getSelectionModel().selectFirst();
  }

}

