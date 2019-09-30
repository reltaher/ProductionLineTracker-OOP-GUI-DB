package productlinetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Controller Class, where the events of the controls are handled.
 *     <p>Date: 09/26/19
 */
public class Controller {

  @FXML private TextField prodNameTA;

  @FXML private TextField manufacturerTA;

  @FXML private ChoiceBox<String> itemTypeChoiceBox;

  @FXML private ComboBox<String> productAmtComboBox;

  /**
   * Method to handle a button event.
   *
   * @brief The method that handles events for the "Add Product" button in the "Product Line" tab.
   *     <p>When the "Add Product" button it clicks, the program accesses the database from the main
   *     class and launches it. If launch was successful, it will then attempt to execute a query
   *     that was instructed. If successful, the statement will execute the query, and a message
   *     will print afterwards stating that it was successful.
   * @return nothing
   */
  @FXML
  void handleEventAddProduct() {
    // Main class object is made to access the database method
    Main database = new Main();
    // Allows button to initialize the database through the Main class
    database.initializeDB();
    // Gets text from Product Name text area in Product Line tab.
    String prodName = prodNameTA.getText();
    // Gets text from Manufacturer text area in Product Line tab.
    String prodManufacturer = manufacturerTA.getText();
    // Gets text from Item Type Choice Box in Product Line tab.
    String itemType = itemTypeChoiceBox.getValue();
    // Prints the values from each input
    System.out.println(
        "\nProduct name: "
            + prodName
            + "\nManufacturer: "
            + prodManufacturer
            + "\nItem Type: "
            + itemType);
  }

  /**
   * Method to handle a button event.
   *
   * @brief The method that handles events for the "Record Production" button in the "Product
   *     Record" tab.
   *     <p>When the "Record Production" button is clicked, the program will output the number that
   *     is selected from the Combo Box. This button will have more functions in the future.
   * @return nothing
   */
  @FXML
  void handleEventRecordProduction() {
    // Gets the value from the Product Quantity Combobox in Product Record tab.
    String prodQuantity = productAmtComboBox.getValue();
    // Prints value that was obtained from prodQuantity
    System.out.println(prodQuantity);
  }

  /**
   * Method to manipulate ComboBox and ChoiceBox.
   *
   * @brief Method which handles the ComboBox and the ChoiceBox methods within the program.
   *     <p>The initialize method populates values in the "productAmtComboBox" ComboBox, which also
   *     has the ability to enter other values, and the method populates theaswell as the
   *     "itemTypeChoiceBox" ChoiceBox aswell.
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
    // Loops through each enum, gets their string value, and adds them into itemTypeChoiceBox
    for (ItemType items : ItemType.values()) {
      itemTypeChoiceBox.getItems().addAll(items.getCode());
    }
    // shows the first value in the item type Choice Box in the "Product Line" tab
    itemTypeChoiceBox.getSelectionModel().selectFirst();
  }
}
