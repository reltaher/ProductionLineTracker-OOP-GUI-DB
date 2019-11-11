package productlinetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The program is a software made in JavaFX where it tracks the number and types of products being
 * made. This file is the Controller Class, where the events of the controls are handled.
 *
 * <p>Date: 09/26/19
 *
 * @author Ramzy El-Taher
 */
public class Controller {

  @FXML private TextField productNameTF;

  @FXML private TextField manufacturerTF;

  @FXML private ChoiceBox<ItemType> itemTypeChoiceBox;

  @FXML private ComboBox<String> productAmtComboBox;

  @FXML private TableView<Product> tableView;

  @FXML private TableColumn<String, Product> productNameColumn;

  @FXML private TableColumn<?, ?> manufacturerColumn;

  @FXML private TableColumn<?, ?> itemTypeColumn;

  @FXML private ListView<Product> prodLineListView;

  @FXML private TextArea productLogTA;

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

    //  Database credentials (Username/Password are temporary)
    final String USER = "";

    final String PASS = "";

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
    } catch (SQLException e) {
      System.out.println("Error: SQL Exception.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Error: Class Not Found.");
      e.printStackTrace();
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
    final ObservableList<Product> data = loadProductList();
    // Sets Cell Value Factory for the Product Name column
    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    // Sets Cell Value Factory for the Manufacturer column
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    // Sets Cell Value Factory for the Item Type column
    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
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
   *     object's fields into the ProductionRecord database.
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
   *     Production Log.
   */
  private void showProduction(ArrayList<ProductionRecord> productionLog) {
    // Splits each word separated by a space in the Production Log into its own index with regex
    String[] log =
        productionLog.toString().substring(1, productionLog.toString().indexOf('.')).split(" ");
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
        if (log[5].equals(id)) {
          // Assigns the 5th index in the Production Log to the product name that matches the ID
          log[5] = name;
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
        Arrays.toString(log).replaceAll("[,]", "").replaceAll("\\[", "").replaceAll("]", "")
            + " "
            // Calendar class that gets the current Year, Date, and Time.
            // Substring (20,24) gets only the current Timezone and displays it at the end
            + Calendar.getInstance(TimeZone.getDefault()).getTime().toString().substring(20, 24)
            + "\n");
  }

  /**
   * The initialize method is used to call other methods that are needed to be executed when the
   * program starts.
   */
  public void initialize() {
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
    addToProductDB();
    loadProductList();
    setupProductLineTable();
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
    // Get the Quantity Value from the Combo Box and convert it into an Integer
    int amtToProduce = Integer.parseInt(productAmtComboBox.getValue());
    // Number which represents the amount of items made
    // int itemCount = 0;
    // ArrayList of ProductionRecord objects
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();
    // Loop that adds an item to the ArrayList depending on the amount that the user selects
    int au = 0;
    int am = 0;
    int vi = 0;
    int vm = 0;
    int counter = 0;
    String selectSerialNum = "SELECT SERIAL_NUM FROM PRODUCTIONRECORD";
    try {
      PreparedStatement psSelectSerialNum = conn.prepareStatement(selectSerialNum);
      ResultSet rsSelectSerialNum = psSelectSerialNum.executeQuery();
      while (rsSelectSerialNum.next()) {
        String serialNum = rsSelectSerialNum.getString("serial_num");
        if (serialNum.contains("AU")) {
          au++;
        }
        if (serialNum.contains("AM")) {
          am++;
        }
        if (serialNum.contains("VI")) {
          vi++;
        }
        if (serialNum.contains("VM")) {
          vm++;
        }
      }
      rsSelectSerialNum.close();
      psSelectSerialNum.close();
      if (itemToProduce.toString().contains("AU")) {
        counter = au;
      }
      if (itemToProduce.toString().contains("AM")) {
        counter = am;
      }
      if (itemToProduce.toString().contains("VI")) {
        counter = vi;
      }
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

      // Stores the ProductionRecord object that was made into the ArrayList

      // Sets the Product ID based on the Item selected in the List View.
      String selectNameID = "SELECT ID, NAME FROM PRODUCT";
      try {
        PreparedStatement psSelectNameID = conn.prepareStatement(selectNameID);
        ResultSet rsSelectNameID = psSelectNameID.executeQuery();
        while (rsSelectNameID.next()) {
          int id = rsSelectNameID.getInt("ID");
          String name = rsSelectNameID.getString("NAME");
          if (prodLineListView.getSelectionModel().getSelectedItem().toString().contains(name)) {
            pr.setProductID(id);
          }
        }
        rsSelectNameID.close();
        psSelectNameID.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      productionRun.add(pr);
    }
    addToProductionDB(productionRun);
    loadProductionLog();

    // Passes the ArrayList of ProductionRecord objects to the "addToProductionDB" method.

    // Gets the item from the Product Record tab and converts it into a string.
    String prodToProduce = prodLineListView.getSelectionModel().getSelectedItem().toString();
    // Gets the value of the amount of items that the user wishes to make.
    String prodQuantity = productAmtComboBox.getValue();
    // Prints value that was obtained from prodQuantity
    System.out.println(
        "Product has been made: " + "\n" + prodToProduce + "\nAmount: " + prodQuantity);
  }
}
