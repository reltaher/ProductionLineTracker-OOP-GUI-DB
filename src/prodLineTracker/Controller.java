package prodLineTracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {

  @FXML private TextField prodNameTA;

  @FXML private TextField manufacturerTA;

  @FXML private ChoiceBox<String> itemTypeCbox;

  @FXML private ComboBox<String> productAmtComboBox;

  /**
   * The method that handles events for the "Add Product" button in the "Product Line" tab.
   *
   * @return nothing
   */
  @FXML
  void handleEventAddProduct() {
    Main DB = new Main();
    DB.initializeDB();
    String prodName = prodNameTA.getText();
    String prodManufacturer = manufacturerTA.getText();
    String itemType = itemTypeCbox.getValue();
    System.out.println(
        "\nProduct name: "
            + prodName
            + "\nManufacturer: "
            + prodManufacturer
            + "\nItem Type: "
            + itemType);
  }

  /**
   * The method that handles events for the "Record Production" button in the "Product Record" tab.
   *
   * @return nothing
   */
  @FXML
  void handleEventRecordProduction() {
    String prodQuantity = productAmtComboBox.getValue();
    System.out.println(prodQuantity);
  }

  /**
   * The initialize method populates values in the "prodAmtCbox" ComboBox and has an option to enter
   * other values.
   *
   * @return nothing
   */
  public void initialize() {
    // Observable List of items that is added in the ComboBox in the "Product Record" tab.
    ObservableList<String> productQuantityList =
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    // Adds numbers 1-10 in the ComboBox as a list in the "Product Record" tab
    productAmtComboBox.setItems(productQuantityList);
    // Allows users to enter other values in the ComboBox in the "Product Record" tab
    productAmtComboBox.setEditable(true);
    // Shows a default value in the ComboBox in the "Product Record" tab
    productAmtComboBox.getSelectionModel().selectFirst();
    // Adds item types in the item type ChoiceBox in the "Product Line" tab
    itemTypeCbox.getItems().addAll("Audio", "AudioMobile", "Visual", "VisualMobile");
    // shows the first value in the item type Choice Box in the "Product Line" tab
    itemTypeCbox.getSelectionModel().selectFirst();
  }
}
