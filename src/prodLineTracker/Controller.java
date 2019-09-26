package prodLineTracker;

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
  private ChoiceBox<String> itemTypeCbox;

  @FXML
  private Button addProduct;

  @FXML
  private TableView<String> tableView;

  @FXML
  private ComboBox<Integer> prodAmtCbox;

  @FXML
  private Button recBtn;


  /**
   * The method that handles events for the "Add Product" button in the "Product Line" tab.
   * @param event the event that tells the user that the button was pressed.
   * @return nothing
   */
  @FXML
  void handleEventAddProduct(ActionEvent event) {
      Main DB = new Main();
      DB.initializeDB();
    System.out.println("Button pressed: " + event.toString());
    }

  /**
   * The method that handles events for the "Record Production" button in the "Product Record"
   * tab.
   * @param event the event that tells the user that the button was pressed.
   * @return nothing
   */
  @FXML
  void handleEventRecordProduction(ActionEvent event) {
    System.out.println("Button pressed: " + event.toString());
  }

  /**
   * The initialize method populates values in the "prodAmtCbox" ComboBox and has an option to
   * enter other values.
   * @return nothing
   */
  public void initialize() {
    //Adds numbers 1-10 in the ComboBox as a list in the "Product Record" tab
    prodAmtCbox.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
    //Allows users to enter other values in the ComboBox in the "Product Record" tab
    prodAmtCbox.setEditable(true);
    //Shows a default value in the ComboBox in the "Product Record" tab
    prodAmtCbox.getSelectionModel().selectFirst();
    //Adds item types in the item type ChoiceBox in the "Product Line" tab
    itemTypeCbox.getItems().addAll("Audio",
        "AudioMobile",
        "Visual",
        "VisualMobile");
    //shows the first value in the item type Choice Box in the "Product Line" tab
    itemTypeCbox.getSelectionModel().selectFirst();

  }

}

