package productlinetracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
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
  private ComboBox<String> productAmtComboBox;

  @FXML
  private TableView<Product> tableView;

  @FXML
  private TableColumn<String, Product> productNameColumn;

  @FXML
  private TableColumn<?, ?> manufacturerColumn;

  @FXML
  private TableColumn<?, ?> itemTypeColumn;

  @FXML
  private Label errorLabel;

  @FXML
  private Label productErrorLbl;

  @FXML
  private Label manufacturerErrorLbl;

  @FXML
  private Label empErrorLbl;

  @FXML
  private Label pwErrorLbl;

  @FXML
  private ListView<Product> prodLineListView;

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

  private Connection conn;

  /**
   * Method which initializes the Database when the program starts. When a database is initialized,
   * a JDBC driver is registered, a connection gets opened, and a statement is created. The method
   * is called from the "initialize()" method, which runs any code contained within it at the start
   * of the program.
   *
   * @return void
   */
  private void initializeDB() {
    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/ProductLineDB";

    // Properties File
    Properties prop = new Properties();
    // FileInputStream could contain an IOException, so it is placed in a try/catch
    try {
      // Load Properties File
      prop.load(new FileInputStream("res/data.properties"));
      // IOException, which catches an issue from FileInputStream
    } catch (IOException fileInputEx) {
      fileInputEx.printStackTrace();
      // General exception, which catches any other issue.
    } catch (Exception ex) {
      System.out.println("Something unusual went wrong. Check code for issues.");
      ex.printStackTrace();
    }
    //  Database credentials (Username/Password are temporary)
    final String USER = "";
    /* Password is obtained from the Properties file in the res folder
     * The password can be changed in the database console using "SET PASSWORD 'pwName';"
     */
    final String PASS = reverseString(prop.getProperty("password"));

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      // STEP 2: Open a connection
      System.out.println("Connecting to Database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      Statement stmt = conn.createStatement();
      // Checks if the created statement is not null
      if (stmt != null) {
        // Message will execute if stmt is not null
        System.out.println("Successfully connected to Database.");
      } else {
        // A NullPointerException is thrown is the statement is null
        throw new NullPointerException("There was a problem creating a statement.");
      }
      stmt.close();
    } catch (SQLException sqlEx) {
      System.out.println("Could not connect to the database. Check the console for issues.");
      sqlEx.printStackTrace();
    } catch (ClassNotFoundException cnfEx) {
      System.out.println("Error: Class Not Found. Check the console for issues.");
      cnfEx.printStackTrace();
    } catch (Exception unusualEx) {
      System.out.println("An unusual exception has occurred. Check the console for issues.");
      unusualEx.printStackTrace();
    }
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
    productAmtComboBox.setItems(productQuantityList);
    // Allows users to enter other values in the ComboBox in the "Product Record" tab
    productAmtComboBox.setEditable(true);
    // Shows a default value in the ComboBox in the "Product Record" tab
    productAmtComboBox.getSelectionModel().selectFirst();
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
      PreparedStatement psInsertIntoProduct = conn.prepareStatement(insertIntoProduct);
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
      PreparedStatement psSelectProductLine = conn.prepareStatement(selectProductLine);
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
    prodLineListView.setItems(data);
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
        PreparedStatement prepareInsertQuery = conn.prepareStatement(insertIntoPR);
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
      PreparedStatement psSelectAllFromPR = conn.prepareStatement(selectAllFromPR);
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
      PreparedStatement psSelectNameID = conn.prepareStatement(selectNameID);
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
   * Method that reverses the order of the text stored for the database password.
   *
   * @param pw The String variable that contains the password.
   * @return reversedPw, which is the reversed password obtained from the method.
   */
  private String reverseString(String pw) {
    if (pw.length() <= 0) {
      return pw;
    }
    return reverseString(pw.substring(1)) + pw.charAt(0);
  }

  /**
   * The initialize method is used to call other methods that are needed to be executed when the
   * program starts.
   */
  public void initialize() {
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
    instructionTA.setEditable(false);
    instructionTA.positionCaret(0);
    productLogTA.setEditable(false);
    itemProducedTA.setEditable(false);
    employeeInfoTA.setEditable(false);
    errorLabel.setVisible(false);
    productErrorLbl.setVisible(false);
    manufacturerErrorLbl.setVisible(false);
    empErrorLbl.setVisible(false);
    pwErrorLbl.setVisible(false);
    initializeDB();
    populateComboBox();
    populateChoiceBox();
    setupProductLineTable();
    loadProductList();
    loadProductionLog();
    testMultiMedia();
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
      removeErrorText();
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

  public void removeErrorText() {
    manufacturerErrorLbl.setVisible(false);
    productErrorLbl.setVisible(false);
    productNameTF.getStyleClass().remove("error");
    manufacturerTF.getStyleClass().remove("error");
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
    // Create a Product Object from the item that the user selects from the List View
    Product itemToProduce = prodLineListView.getSelectionModel().getSelectedItem();
    if (itemToProduce == null) {
      errorLabel.setVisible(true);
      errorLabel.setStyle("-fx-text-fill: red");
      errorLabel.setText("Please select an item to produce.");
      // errorLabel.getStyleClass().add("error");

    } else {
      errorLabel.setVisible(false);
      // Get the Quantity Value from the Combo Box and convert it into an Integer
      int amtToProduce = Integer.parseInt(productAmtComboBox.getValue());
      // ArrayList of ProductionRecord objects
      ArrayList<ProductionRecord> productionRun = new ArrayList<>();
      // Audio item counter
      int au = 0;
      // AudioMobile item counter
      int am = 0;
      // Visual item counter
      int vi = 0;
      // VisualMobile item counter
      int vm = 0;
      // counter variable used for a ProductionRecord object.
      int counter = 0;
      String selectSerialNum = "SELECT SERIAL_NUM FROM PRODUCTIONRECORD";
      try {
        PreparedStatement psSelectSerialNum = conn.prepareStatement(selectSerialNum);
        ResultSet rsSelectSerialNum = psSelectSerialNum.executeQuery();
        while (rsSelectSerialNum.next()) {
          String serialNum = rsSelectSerialNum.getString("serial_num");
          // auto-increment Audio counter if SERIAL_NUM table contains "AU"
          if (serialNum.contains("AU")) {
            au++;
          }
          // auto-increment AudioMobile counter if SERIAL_NUM table contains "AM"
          if (serialNum.contains("AM")) {
            am++;
          }
          // auto-increment Visual counter if SERIAL_NUM table contains "VI"
          if (serialNum.contains("VI")) {
            vi++;
          }
          // auto-increment VisualMobile counter if SERIAL_NUM table contains "VM"
          if (serialNum.contains("VM")) {
            vm++;
          }
        }
        rsSelectSerialNum.close();
        psSelectSerialNum.close();
        // Assign counter variable to Audio item counter if ListView item is an AU ItemType.
        if (itemToProduce.toString().contains("AU")) {
          counter = au;
        }
        // Assign counter variable to AudioMobile item counter if ListView item is an AM ItemType.
        if (itemToProduce.toString().contains("AM")) {
          counter = am;
        }
        // Assign counter variable to Visual item counter if ListView item is a VI ItemType.
        if (itemToProduce.toString().contains("VI")) {
          counter = vi;
        }
        // Assign counter variable to VisualMobile item counter if ListView item is a VM ItemType.
        if (itemToProduce.toString().contains("VM")) {
          counter = vm;
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      /* ProductionRecord object that takes in the Product object of the user selection and the
      amount of items that is made*/
      for (int itemCount = 1; itemCount <= amtToProduce; itemCount++) {
        ProductionRecord pr = new ProductionRecord(itemToProduce, counter++);
        String selectNameID = "SELECT ID, NAME FROM PRODUCT";
        try {
          PreparedStatement psSelectNameID = conn.prepareStatement(selectNameID);
          ResultSet rsSelectNameID = psSelectNameID.executeQuery();
          while (rsSelectNameID.next()) {
            int id = rsSelectNameID.getInt("ID");
            String name = rsSelectNameID.getString("NAME");
            // Sets the Product ID based on the Item selected in the List View.
            if (prodLineListView.getSelectionModel().getSelectedItem().toString().contains(name)) {
              pr.setProductID(id);
            }
          }
          rsSelectNameID.close();
          psSelectNameID.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
        // Stores the ProductionRecord object that was made into the ArrayList
        productionRun.add(pr);
      }
      // Passes the ArrayList of ProductionRecord objects to the "addToProductionDB" method.
      addToProductionDB(productionRun);
      // Calls loadProductionLog method
      loadProductionLog();
      // Gets the item from the Product Record tab and converts it into a string.
      String prodToProduce = prodLineListView.getSelectionModel().getSelectedItem().toString();
      // Gets the value of the amount of items that the user wishes to make.
      String prodQuantity = productAmtComboBox.getValue();
      // Prints value that was obtained from prodQuantity
      System.out.println(
          "Product has been made: " + "\n" + prodToProduce + "\nAmount: " + prodQuantity);
      // Sets the item produced to the Text Area in the Produce tab.
      itemProducedTA.setText(
          "Product has been made: " + "\n" + prodToProduce + "\nAmount: " + prodQuantity);
    }
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
    String empName = employeeNameTF.getText();
    String empPassword = employeePasswordTF.getText();
    if (empName.isEmpty() && !empPassword.isEmpty()) {
      empErrorLbl.setVisible(true);
      pwErrorLbl.setVisible(false);
      empErrorLbl.setStyle("-fx-text-fill: red");
      empErrorLbl.setText("Enter Employee Name.");
    }

    if (empPassword.isEmpty() && !empName.isEmpty()) {
      empErrorLbl.setVisible(false);
      pwErrorLbl.setVisible(true);
      pwErrorLbl.setStyle("-fx-text-fill: red");
      pwErrorLbl.setText("Enter Password.");
    }
    if (empName.isEmpty()) {
      empErrorLbl.setVisible(true);
      empErrorLbl.setStyle("-fx-text-fill: red");
      empErrorLbl.setText("Enter Employee Name.");
    } else if (empPassword.isEmpty()) {
      empErrorLbl.setVisible(false);
      pwErrorLbl.setVisible(true);
      pwErrorLbl.setStyle("-fx-text-fill: red");
      pwErrorLbl.setText("Enter Password.");
    } else {
      empErrorLbl.setVisible(false);
      pwErrorLbl.setVisible(false);
      Employee employee = new Employee(empName, empPassword);
      employeeInfoTA.setText(employee.toString());
    }
  }
}
