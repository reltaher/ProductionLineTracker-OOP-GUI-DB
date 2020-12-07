package productlinetracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The Controller Class handles the events and the functionality of the program.
 *
 * <p>Date: 11/14/19
 *
 * @author Ramzy El-Taher
 */
public class Controller {

  @FXML
  private TextField productNameTF;

  @FXML
  private TextField manufacturerTF;

  @FXML
  private ChoiceBox<ItemType> itemTypeChoiceBox;

  @FXML
  private ChoiceBox<String> productFilter;

  @FXML
  private ComboBox<String> productAmount;

  @FXML
  private TableView<Product> tableView;

  @FXML
  private TableColumn<String, Product> productNameColumn;

  @FXML
  private TableColumn<?, ?> manufacturerColumn;

  @FXML
  private TableColumn<?, ?> itemTypeColumn;

  @FXML
  private Label itemErrorLabel;

  @FXML
  private Label productErrorLbl;

  @FXML
  private Label manufacturerErrorLbl;

  @FXML
  private Label employeeNameErrorLabel;

  @FXML
  private Label employeePasswordErrorLabel;

  @FXML
  private ListView<Product> productLine;

  @FXML
  private TextArea instructionTA;

  @FXML
  private TextArea productLogTA;

  @FXML
  private TextField employeeNameTF;

  @FXML
  private TextField employeePasswordTF;

  @FXML
  private TextField productSearchTF;

  @FXML
  private TextArea employeeInfoTA;

  @FXML
  private TextArea itemProducedTA;

  private Connection connection;

  /**
   * Method which initializes the Database when the program starts. When a database is initialized,
   * a JDBC driver is registered, a connection gets opened, and a statement is created. The method
   * is called from the "initialize()" method, which runs any code contained within it at the start
   * of the program.
   *
   * @return void
   */
  private void initializeDB() {
    Database database = new Database();
    connection = database.getConnection();
  }

  /**
   * Method that populates the Product Amount ComboBox, which is located in the Produce tab. The
   * ComboBox is populated with an ObservableList of String variables, numbered 1-10. The ComboBox
   * also selects the first item within the ObservableList, which in this case would be "1".
   *
   * @return void
   */
  private void populateComboBox() {
    // Observable List of items that is added in the ComboBox in the "Product Record" tab.
    ObservableList<String> productQuantityList =
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    // Adds numbers 1-10 in the ComboBox as a list in the "Product Record" tab
    productAmount.setItems(productQuantityList);
    // Allows users to enter other values in the ComboBox in the "Product Record" tab
    productAmount.setEditable(true);
    // Shows a default value in the ComboBox in the "Product Record" tab
    productAmount.getSelectionModel().selectFirst();
  }

  /**
   * Method that populates the Item Type ChoiceBox, located in the Product Line tab. The ChoiceBox
   * is populated with the Item Type Enums "AUDIO", "AUDIOMOBILE", "VISUAL", and "VISUALMOBILE". The
   * Choice Box also selects the first item in the Item Type list by default, which in this case
   * would be "AUDIO".
   *
   * @return void
   */
  private void populateChoiceBox() {
    // Populates the item type choice box with the ItemType enum values
    itemTypeChoiceBox.getItems().addAll(ItemType.values());
    // shows the first value in the item type Choice Box in the "Product Line" tab
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    ObservableList<String> productFilterList =
        FXCollections.observableArrayList("Name", "Manufacturer", "Type");
    productFilter.getItems().addAll(productFilterList);
    productFilter.getSelectionModel().selectFirst();
  }

  /**
   * Method that executes an INSERT INTO query for the Product table in the H2 Database. The query
   * inserts the text from the name TextField into the "NAME" column, the text from the manufacturer
   * TextField into the "MANUFACTURER" column, and the code obtained from the ItemType enums into
   * the "TYPE" column.
   *
   * @return void
   */
  private void addToProductDB() {
    try {
      System.out.println("Inserting Product to Database...");
      // Execute an INSERT INTO query for NAME, MANUFACTURER, and TYPE
      String insertIntoProduct =
          "INSERT INTO Product (NAME, MANUFACTURER, TYPE)" + "VALUES(?, ?, ?)";
      // Prepares a statement for the SQL query
      PreparedStatement psInsertIntoProduct = connection.prepareStatement(insertIntoProduct);
      // Sets the Text obtained from the Product Name TextField
      psInsertIntoProduct.setString(1, productNameTF.getText());
      psInsertIntoProduct.setString(2, manufacturerTF.getText());
      psInsertIntoProduct.setString(3, itemTypeChoiceBox.getValue().getCode());
      // After the sets for the PreparedStatement, an executeUpdate is called for the query
      psInsertIntoProduct.executeUpdate();
      // If insertion was successful, a message will print to the console saying that it worked.
      System.out.println("Insertion successful. Product has been added.");
      psInsertIntoProduct.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println("Error: SQL Error");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Method that executes a SELECT query for the Product table in the H2 database. The query will
   * select the NAME, TYPE, and MANUFACTURER columns from the Product table, Prepare a statement
   * using PreparedStatement, and executes the query with a ResultSet. With the use of the
   * ResultSet, a Widget object is made that takes in the selected items from name, type,
   * manufacturer. The Widget object is then stored into an ArrayList of Product objects named
   * "productLine". The ArrayList of product objects is finally stored into an ObservableList, and
   * the ObservableList gets returned.
   *
   * @return an ObservableList fill with the ArrayList of Product objects named "productLine".
   */
  private ObservableList<Product> loadProductList() {
    // ArrayList which holds all of the products that can be produced.
    ArrayList<Product> productLine = new ArrayList<>();
    try {
      // SELECT SQL query for NAME, TYPE, and MANUFACTURER in the PRODUCT table.
      String selectProductLine = "SELECT NAME, TYPE, MANUFACTURER FROM PRODUCT";
      // Prepares a statement for the SQL query.
      PreparedStatement psSelectProductLine = connection.prepareStatement(selectProductLine);
      // ResultSet which executes the PreparedStatement.
      ResultSet rsSelectProductLine = psSelectProductLine.executeQuery();
      // Loops through each row selected in the Product table in the database.
      while (rsSelectProductLine.next()) {
        // Stores each row from the database into an ArrayList as a Widget Object.
        productLine.add(
            new Widget(
                rsSelectProductLine.getString("name"),
                ItemType.fromString(rsSelectProductLine.getString("type")),
                rsSelectProductLine.getString("manufacturer")));
      }
      // Closes the ResultSet once it is finished with execution.
      rsSelectProductLine.close();
      psSelectProductLine.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    // Observable List of products which are stored in each respective column.
    return FXCollections.observableArrayList(productLine);
  }

  /**
   * Method which sets up the Product Line Table. The TableView and the ListView are both populated
   * with items from an ObservableList which holds an Arraylist of Product objects, which was
   * populated from the "loadProductList()" method.
   *
   * @return void
   */
  private void setupProductLineTable() {
    // Console message that informs the user that the products are loading.
    System.out.println("Loading Products...");
    // ObservableList with the ArrayList of Product objects populated from a different method.
    ObservableList<Product> data = loadProductList();
    // Sets Cell Value Factory for the Product Name column
    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    // Sets Cell Value Factory for the Manufacturer column
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    // Sets Cell Value Factory for the Item Type column
    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    switch (productFilter.getSelectionModel().getSelectedItem()) {
      case "Name":
        data =
            FXCollections.observableArrayList(
                data.stream()
                    .filter(
                        p ->
                            p.getName()
                                .toLowerCase()
                                .contains(productSearchTF.getText().toLowerCase()))
                    .collect(Collectors.toList()));
        break;
      case "Manufacturer":
        data =
            FXCollections.observableArrayList(
                data.stream()
                    .filter(
                        p ->
                            p.getManufacturer()
                                .toLowerCase()
                                .contains(productSearchTF.getText().toLowerCase()))
                    .collect(Collectors.toList()));
        break;
      case "Type":
        data =
            FXCollections.observableArrayList(
                data.stream()
                    .filter(
                        p ->
                            p.getType()
                                .getCode()
                                .toLowerCase()
                                .contains(productSearchTF.getText().toLowerCase()))
                    .collect(Collectors.toList()));
        break;
      default:
        data = loadProductList();
        break;
    }
    // loadFilteredProductList(filteredProducts);

    // Populates the Table View with the items from the ObservableList.
    tableView.setItems(data);
    // Populates the List View with the items from the ObservableList.
    productLine.setItems(data);
  }

  /**
   * Method that loops through the productionRun, inserting productionRecord object information into
   * the ProductionRecord database table with the use of an INSERT INTO query. Each column within
   * the ProductionRecord table is set to the respective accessor from the ProductionRecord object
   * "productLine".
   *
   * @param productionRun An ArrayList of ProductionRecord objects, used to store a ProductionRecord
   *                      object's fields into the ProductionRecord database.
   */
  private void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    /* Loop through the productionRun,
    inserting productionRecord object information into the ProductionRecord database table. */
    for (ProductionRecord productLine : productionRun) {
      String insertIntoPR =
          "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
              + "VALUES (?, ?, ?)";
      try {
        PreparedStatement prepareInsertQuery = connection.prepareStatement(insertIntoPR);
        // prepareInsertQuery.setInt(1, productLine.getProductionNumber());
        prepareInsertQuery.setInt(1, productLine.getProductID());
        prepareInsertQuery.setString(2, productLine.getSerialNumber());
        prepareInsertQuery.setTimestamp(
            3, new java.sql.Timestamp(productLine.getDateProduced().getTime()));
        prepareInsertQuery.executeUpdate();
        prepareInsertQuery.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Method that executes a SELECT query for the ProductionRecord table in the H2 database. The
   * ResultSet that is being executed in this method takes each item from the ProductionRecord table
   * and stores the information into a ProductionRecord object. The object is stored into an
   * ArrayList of ProductionRecord objects names "productionLog", and the ArrayList is then passed
   * on to a method named "showProduction".
   *
   * @return void
   */
  private void loadProductionLog() {
    String selectAllFromPR = "SELECT * FROM PRODUCTIONRECORD";
    try {
      PreparedStatement psSelectAllFromPR = connection.prepareStatement(selectAllFromPR);
      ResultSet rsSelectAllFromPR = psSelectAllFromPR.executeQuery();

      // When an item is added to the log, the TextArea is cleared to prevent duplication.
      productLogTA.clear();
      while (rsSelectAllFromPR.next()) {
        // Create ProductionRecord objects from the database columns
        ProductionRecord pr =
            new ProductionRecord(
                rsSelectAllFromPR.getInt("PRODUCTION_NUM"),
                rsSelectAllFromPR.getInt("PRODUCT_ID"),
                rsSelectAllFromPR.getString("SERIAL_NUM"),
                rsSelectAllFromPR.getTimestamp("DATE_PRODUCED"));
        // Create an ArrayList that holds ProductionRecord objects used for production log
        ArrayList<ProductionRecord> productionLog = new ArrayList<>();
        // Populate productionLog ArrayList with the ProductionRecord objects
        productionLog.add(pr);
        /* Stores Production Record Info, time produced in Hours:Minutes:Seconds, and the
        local Timezone to the Production Log tab. */
        showProduction(productionLog);
      }
      rsSelectAllFromPR.close();
      psSelectAllFromPR.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Method that displays the Production Log taken from information stored in the productionLog
   * ArrayList in the Production Log tab. The Production Log will display the Production Number,
   * Product ID (Product Name), Serial Number, and the date produced along with the current
   * timezone.
   *
   * @param productionLog The ArrayList of ProductionRecord objects used to be displayed in the
   *                      Production Log.
   */
  private void showProduction(ArrayList<ProductionRecord> productionLog) {
    // Splits each word separated by a space in the Production Log into its own index with regex
    String[] log = productionLog.toString().split(" ");
    // SQL Query to Select ID and Name from Product Table.
    String selectNameID = "SELECT ID, NAME FROM PRODUCT";
    try {
      // PreparedStatement - Prepares to execute the SQL query
      PreparedStatement psSelectNameID = connection.prepareStatement(selectNameID);
      // ResultSet - Executes the SQL query
      ResultSet rsSelectNameID = psSelectNameID.executeQuery();
      // Loops through the selected columns in the Product Table
      while (rsSelectNameID.next()) {
        // Stores the data in the ID column as a String
        String id = rsSelectNameID.getString("ID");
        // Stores the data in the Name column as a String
        String name = rsSelectNameID.getString("NAME");
        // Compares the Product ID (5th index) in the Production Log with the ID in the ID Column
        if (log[4].equals(id)) {
          // Assigns the 5th index in the Production Log to the product name that matches the ID
          log[4] = name;
        }
      }
      rsSelectNameID.close();
      psSelectNameID.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    productLogTA.appendText(
        /* Converts the contents of the ArrayList to a string
         * Substring starts at 1 to prevent a "[" from displaying at the start.
         * Substring ends at the index of "." to prevent displaying milliseconds. */
        Arrays.toString(log).replaceAll("[\\[\\],]", "") + "\n");
  }

  /**
   * The initialize method is used to call other methods that are needed to be executed when the
   * program starts.
   */
  public void initialize() {
    loadInstructionTextFile();
    setTextAreasUneditable();
    hideErrorText();
    initializeDB();
    populateComboBox();
    populateChoiceBox();
    setupProductLineTable();
    loadProductList();
    loadProductionLog();
    testMultiMedia();
  }

  private void loadInstructionTextFile() {
    String line;
    try {
      // FileInputStream which reads the instructions file
      InputStream fileInputStream = new FileInputStream("res/instructions.txt");
      // Reader which reads the FileInputStream. UTF 8 is needed for any character to be written.
      Reader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
      // BufferedReader which reads reader.
      BufferedReader bufferedReader = new BufferedReader(reader);
      // Reads each line from the instructions txt file
      while ((line = bufferedReader.readLine()) != null) {
        // appends each line to the TextArea in the Info tab.
        instructionTA.appendText(line + "\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    instructionTA.positionCaret(0);
  }

  private void setTextAreasUneditable() {
    instructionTA.setEditable(false);
    productLogTA.setEditable(false);
    itemProducedTA.setEditable(false);
    employeeInfoTA.setEditable(false);
  }

  /**
   * Method that tests the functionality of the MultiMedia.
   *
   * @return void
   */
  private static void testMultiMedia() {
    AudioPlayer newAudioProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct =
        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }

  /**
   * Method that handles the event when the "Add Product" button is pressed in the Product Line tab.
   * When the button is clicked, the method calls two other methods in the program; one that adds
   * data to the Product table, and one that displays the data from the Product table to the Table
   * View and List View. any text entered in the name TextField, manufacturer TextField, and the
   * ItemType ChoiceBox selection will be added to the Product table in the database, and the data
   * that was inserting into the database will be added onto the Table View and ListView.
   *
   * @return void
   */
  @FXML
  void handleEventAddProduct() {
    if (manufacturerTextFieldIsEmpty() && !productTextFieldIsEmpty()) {
      requestUserToEnterManufacturer();
    }
    if (productTextFieldIsEmpty() && !manufacturerTextFieldIsEmpty()) {
      requestUserToEnterManufacturer();
    }
    if (productTextFieldIsEmpty()) {
      productNameTF.requestFocus();
      requestUserToEnterProduct();
    } else if (manufacturerTextFieldIsEmpty()) {
      manufacturerTF.requestFocus();
      requestUserToEnterManufacturer();
    } else { // Execute code if both TextFields are filled in
      hideErrorText();
      addToProductDB();
      loadProductList();
      setupProductLineTable();
      clearTextFields();
    }
  }

  public void requestUserToEnterProduct() {
    productNameTF.getStyleClass().add("error");
    manufacturerTF.getStyleClass().remove("error");
    productErrorLbl.setVisible(true);
    manufacturerErrorLbl.setVisible(false);
    productErrorLbl.setStyle("-fx-text-fill: red");
    productErrorLbl.setText("Enter a Product.");
  }

  public boolean manufacturerTextFieldIsEmpty() {
    return manufacturerTF.getText().isEmpty();
  }

  public boolean productTextFieldIsEmpty() {
    return productNameTF.getText().isEmpty();
  }

  public void hideErrorText() {
    manufacturerErrorLbl.setVisible(false);
    productErrorLbl.setVisible(false);
    productNameTF.getStyleClass().remove("error");
    manufacturerTF.getStyleClass().remove("error");
    employeeNameErrorLabel.setVisible(false);
    employeePasswordErrorLabel.setVisible(false);
    itemErrorLabel.setVisible(false);
  }

  public void requestUserToEnterManufacturer() {
    productNameTF.getStyleClass().remove("error");
    manufacturerTF.getStyleClass().add("error");
    manufacturerErrorLbl.setVisible(true);
    productErrorLbl.setVisible(false);
    manufacturerErrorLbl.setStyle("-fx-text-fill: red");
    manufacturerErrorLbl.setText("Enter a manufacturer.");
  }

  public void clearTextFields() {
    productNameTF.clear();
    manufacturerTF.clear();
  }

  /**
   * Method that handles the event when the "Record Production" button is pressed in the "Produce"
   * tab. When the button is pressed, the data that the user selects from the ListView will be
   * stored as an object, and the object is stored under an ArrayList that holds ProductionRecord
   * objects. The data from the ArrayList is inserted into the ProductionRecord table, and the
   * program converts the data stored into the ArrayList into the data listed in the
   * ProductionRecord table. The data from the ProductionRecord table is then selected from the
   * database and is displayed on the Text Area in the Production Log tab.
   *
   * @return void
   */
  @FXML
  void handleEventRecordProduction() {
    Product itemToProduce = productLine.getSelectionModel().getSelectedItem();
    if (itemToProduce == null) {
      askUserToSelectItemToProduce();
    } else {
      itemErrorLabel.setVisible(false);
      ProductionItemCounter items = new ProductionItemCounter();
      items.countItemsFromDatabase();
      ArrayList<ProductionRecord> productionRun = new ArrayList<>();
      int amountToProduce = Integer.parseInt(productAmount.getValue());
      int productionRecordCounter = getItemCounter(items.getProductionRecordCounter(),
          itemToProduce, items);
      storeProductionRecordID(amountToProduce, itemToProduce, productionRecordCounter,
          productionRun);
      addToProductionDB(productionRun);
      loadProductionLog();
      String productToProduce = productLine.getSelectionModel().getSelectedItem().toString();
      String productQuantity = productAmount.getValue();
      printProductDetails(productToProduce, productQuantity);
    }
  }

  public int getItemCounter(int productionRecordCounter, Product itemToProduce,
      ProductionItemCounter items) {
    int counter = productionRecordCounter;
    if (itemToProduce.toString().contains("AU")) {
      counter = items.getAudioItemCounter();
    }
    if (itemToProduce.toString().contains("AM")) {
      counter = items.getAudioMobileItemCounter();
    }
    if (itemToProduce.toString().contains("VI")) {
      counter = items.getVisualItemCounter();
    }
    if (itemToProduce.toString().contains("VM")) {
      counter = items.getVisualMobileItemCounter();
    }
    return counter;
  }

  public void askUserToSelectItemToProduce() {
    itemErrorLabel.setVisible(true);
    itemErrorLabel.setStyle("-fx-text-fill: red");
    itemErrorLabel.setText("Please select an item to produce.");
  }

  public void storeProductionRecordID(int amountToProduce, Product itemToProduce,
      int productionRecordCounter, ArrayList<ProductionRecord> productionRun) {
    for (int itemCount = 1; itemCount <= amountToProduce; itemCount++) {
      ProductionRecord productRecord = new ProductionRecord(itemToProduce,
          productionRecordCounter++);
      String selectNameID = "SELECT ID, NAME FROM PRODUCT";
      try {
        PreparedStatement psSelectNameID = connection.prepareStatement(selectNameID);
        ResultSet rsSelectNameID = psSelectNameID.executeQuery();
        while (rsSelectNameID.next()) {
          int id = rsSelectNameID.getInt("ID");
          String name = rsSelectNameID.getString("NAME");
          // Sets the Product ID based on the Item selected in the List View.
          if (productLine.getSelectionModel().getSelectedItem().toString().contains(name)) {
            productRecord.setProductID(id);
          }
        }
        rsSelectNameID.close();
        psSelectNameID.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      productionRun.add(productRecord);
    }
  }

  public void printProductDetails(String productToProduce, String productQuantity) {
    System.out.println(
        "Product has been made: " + "\n" + productToProduce + "\nAmount: " + productQuantity);
    // Sets the item produced to the Text Area in the Produce tab.
    itemProducedTA.setText(
        "Product has been made: " + "\n" + productToProduce + "\nAmount: " + productQuantity);
  }

  /**
   * Method that calls the "setupProductLineTable" method upon the button being clicked. This
   * onAction method is used for the puspose of searching a product by name, manufacturer, or type.
   */
  @FXML
  void onActionSearchProduct() {
    setupProductLineTable();
  }

  /**
   * Method that gets the text of the employee's username and password, creates an Employee object
   * of the information obtained from the TextFields, and prints the Employee's information to a
   * TextArea.
   */
  @FXML
  void onActionSubmitEmployeeInfo() {
    String employeeName = employeeNameTF.getText();
    String employeePassword = employeePasswordTF.getText();

    if (employeeName.isEmpty() && !employeePassword.isEmpty()) {
      askUserToEnterEmployeeName();
    }
    if (employeePassword.isEmpty() && !employeeName.isEmpty()) {
      employeePasswordErrorLabel.setVisible(false);
      askUserToEnterEmployeePassword();
    }
    if (employeeName.isEmpty()) {
      askUserToEnterEmployeeName();
    } else if (employeePassword.isEmpty()) {
      askUserToEnterEmployeePassword();
    } else {
      clearEmployeeTextFieldErrorLabels();
      displayEmployeeInformation(employeeName, employeePassword);
    }
  }

  private void askUserToEnterEmployeeName() {
    employeeNameErrorLabel.setVisible(true);
    employeeNameErrorLabel.setStyle("-fx-text-fill: red");
    employeeNameErrorLabel.setText("Enter Employee Name.");
  }

  private void askUserToEnterEmployeePassword() {
    employeeNameErrorLabel.setVisible(false);
    employeePasswordErrorLabel.setVisible(true);
    employeePasswordErrorLabel.setStyle("-fx-text-fill: red");
    employeePasswordErrorLabel.setText("Enter Password.");
  }

  private void clearEmployeeTextFieldErrorLabels() {
    employeeNameErrorLabel.setVisible(false);
    employeePasswordErrorLabel.setVisible(false);
  }

  private void displayEmployeeInformation(String employeeName, String employeePassword) {
    Employee employee = new Employee(employeeName, employeePassword);
    employeeInfoTA.setText(employee.toString());
  }
}
