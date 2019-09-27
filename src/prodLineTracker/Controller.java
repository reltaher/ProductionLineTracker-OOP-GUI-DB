package prodLineTracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {

  public Tab productLineTab;
  public Label productNameLabel;
  public Label manufacturerLabel;
  public Label itemTypeLabel;
  public Button addProduct;
  public TableView tableView;
  public TableColumn productNameColumn;
  public TableColumn manufacturerColumn;
  public TableColumn itemTypeColumn;
  public Tab productRecordTab;
  public Button recBtn;

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
    //Main class object is made to access the database method
    Main DB = new Main();
    //Allows button to initialize the database through the Main class
    DB.initializeDB();
    //Gets text from Product Name text area in Product Line tab.
    String prodName = prodNameTA.getText();
    //Gets text from Manufacturer text area in Product Line tab.
    String prodManufacturer = manufacturerTA.getText();
    //Gets text from Item Type Choice Box in Product Line tab.
    String itemType = itemTypeCbox.getValue();
    //Prints the values from each input
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
    //Gets the value from the Product Quantity Combobox in Product Record tab.
    String prodQuantity = productAmtComboBox.getValue();
    //Prints value that was obtained from prodQuantity
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
