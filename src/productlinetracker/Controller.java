package productlinetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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

  @FXML private ChoiceBox<ItemType> itemTypeChoiceBox;

  @FXML private ComboBox<String> productAmtComboBox;

  @FXML private TableView<Product> tableView;

  @FXML private TableColumn<String, Product> productNameColumn;

  @FXML private TableColumn<?, ?> manufacturerColumn;

  @FXML private TableColumn<?, ?> itemTypeColumn;

  @FXML private ListView<Product> prodLineListView;

  @FXML private TextArea productLogTA;

  Statement stmt;

  Connection conn;

  /**
   * Method to Initialize Database.
   *
   * @brief Connects to H2 database and executes a query.
   *     <p>The initializeDB method connects to the H2 Database made within the IDE. Once connected,
   *     a statement is created and a SQL command is executed. If the query is successfully
   *     executed, a message will state that the product record has been inserted into the database.
   * @return nothing
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
      stmt = conn.createStatement();
      System.out.println("Successfully connected to Database.");
    } catch (SQLException e) {
      System.out.println("Error: SQL Exception.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Error: Class Not Found.");
      e.printStackTrace();
    }
  }

  /** Method to load name, type, manufacturer into a product table */
  private ObservableList<Product> loadProductList() {
    System.out.println("Loading Products...");
    // ArrayList which holds all of the products that can be produced.
    ArrayList<Product> productLine = new ArrayList<>();

    // Observable List of products which are stored in each respective column
    try {
      // Execute a SELECT query
      String loadProdList = "SELECT NAME, TYPE, MANUFACTURER FROM PRODUCT";
      PreparedStatement prepareProdList = conn.prepareStatement(loadProdList);
      ResultSet rsProdList = prepareProdList.executeQuery();

      // Stores each row from the database into an ArrayList as a Widget Object
      while (rsProdList.next()) {
        productLine.add(
            new Widget(
                rsProdList.getString("name"),
                rsProdList.getString("manufacturer"),
                ItemType.fromString(rsProdList.getString("type"))));
        }
      rsProdList.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println("Error: SQL Exception");
    }
    return FXCollections.observableArrayList(productLine);
  }

  /** Method to save name, manufacturer, and type to the Product table in the database. When the Add
   * Product Button is pressed, this method is executed. */
  private void addProductToDB() {
    try {
      // Execute an INSERT INTO query
      System.out.println("Inserting Product to Database...");
      String saveProdSQL = "INSERT INTO Product (name, manufacturer, type)" + "VALUES(?, ?, ?)";
      PreparedStatement saveProdPS = conn.prepareStatement(saveProdSQL);
      saveProdPS.setString(1, prodNameTA.getText());
      saveProdPS.setString(2, manufacturerTA.getText());
      saveProdPS.setString(3, itemTypeChoiceBox.getValue().getCode());
      saveProdPS.executeUpdate();
      // If insertion was successful, a message will print to the console saying that it worked.
      System.out.println("Insertion successful. Product has been added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println("Error: SQL Error");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Method that saves Production Log info to ProductionRecord database table */
  private void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    /* Loop through the productionRun,
       inserting productionRecord object information into the ProductionRecord database table. */
    for (ProductionRecord productLine : productionRun) {
      String insertItemRecord =
          "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
              + "VALUES (?, ?, ?, ?)";
      long d = System.currentTimeMillis();
      try {
      PreparedStatement prepareInsertQuery = conn.prepareStatement(insertItemRecord);
      prepareInsertQuery.setInt(1, productLine.getProductionNumber());
      prepareInsertQuery.setInt(2, productLine.getProductID());
      prepareInsertQuery.setString(3, productLine.getSerialNumber());
      prepareInsertQuery.setTimestamp(4, new Timestamp(d));
      prepareInsertQuery.executeUpdate();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  /** Method that loads Production Log info from ProductionRecord database table */
  private void loadProductLog() {
    String loadProdLogSQL = "SELECT * FROM PRODUCTIONRECORD";
    try {
      PreparedStatement loadProdLogPS = conn.prepareStatement(loadProdLogSQL);
      ResultSet loadProdLogRS = loadProdLogPS.executeQuery();

      // When an item is added to the log, the TextArea is cleared to prevent duplication.
      productLogTA.clear();
      while (loadProdLogRS.next()) {
        ProductionRecord pr =
            new ProductionRecord(
                loadProdLogRS.getInt("production_num"),
                loadProdLogRS.getInt("product_id"),
                loadProdLogRS.getString("serial_num"),
                loadProdLogRS.getTimestamp("date_produced"));
        //Populate productionLog ArrayList
        ArrayList<ProductionRecord> productionLog = new ArrayList<>();
        productionLog.add(pr);

        /* Stores Production Record Info, time produced in Hours:Minutes:Seconds, and the
        local Timezone to the Production Log tab. */
        showProduction(productionLog);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * method that populates the TextArea on the Production Log tab with the information from the
   * productionLog, replacing the productId with the product name
   */
  public void showProduction(ArrayList<ProductionRecord> productionLog) {
    productLogTA.appendText
        (productionLog.toString().substring(1, productionLog.toString().indexOf('.')) + " "
        + Calendar.getInstance(TimeZone.getDefault()).getTime().toString().substring(20, 24)
        + "\n");
  }

  /** Method that sets the columns and does the setCellValueFactory in the TableView. */
  public void setupProductLineTable() {
    ObservableList<Product> data = loadProductList();
    productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    itemTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
    tableView.setItems(data);
    prodLineListView.setItems(data);
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
    initializeDB();
    // Calls the method that sets up the Product Line Table.
    setupProductLineTable();
    loadProductList();
    loadProductLog();
    // Observable List of items that is added in the ComboBox in the "Product Record" tab.
    ObservableList<String> productQuantityList =
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    // Adds numbers 1-10 in the ComboBox as a list in the "Product Record" tab
    productAmtComboBox.setItems(productQuantityList);
    // Allows users to enter other values in the ComboBox in the "Product Record" tab
    productAmtComboBox.setEditable(true);
    // Shows a default value in the ComboBox in the "Product Record" tab
    productAmtComboBox.getSelectionModel().selectFirst();

    // Populates the item type choice box with the ItemType enum values
    itemTypeChoiceBox.getItems().addAll(ItemType.values());
    // shows the first value in the item type Choice Box in the "Product Line" tab
    itemTypeChoiceBox.getSelectionModel().selectFirst();
    testMultiMedia();
  }

  private static void testMultiMedia() {
    AudioPlayer newAudioProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct =
        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
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
    addProductToDB();
    loadProductList();
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
    // Gets the item from the Product Record tab and converts it into a string.
    String prodToProduce = prodLineListView.getSelectionModel().getSelectedItem().toString();

    // Gets the quantity from the ComboBox
    int productQuantity = Integer.parseInt(productAmtComboBox.getValue());

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();
    
    String selectDBItem = "SELECT * FROM PRODUCT";
    try {
      PreparedStatement prepareSelectQuery = conn.prepareStatement(selectDBItem);
      ResultSet rs = prepareSelectQuery.executeQuery();
      int itemRecordProdNum = 0;
      while (rs.next()) {
        Product aProduct = new Widget(
            rs.getString("name"),
            rs.getString("manufacturer"),
            ItemType.fromString(rs.getString("type"))
        );

        ProductionRecord pr = new ProductionRecord(aProduct, itemRecordProdNum);
        productionRun.add(pr);
        if (prodLineListView
            .getSelectionModel()
            .getSelectedItem()
            .toString()
            .equals(aProduct.toString())) {
          for (int i = 0; i < productQuantity; i++) {
            addToProductionDB(productionRun);
            // Calls the loadProductLog method, which adds a new production log line.
            loadProductLog();
            }
        }
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // Gets the value from the combo box in the Product Record tab.

    String prodQuantity = productAmtComboBox.getValue();
    // Prints value that was obtained from prodQuantity
    System.out.println(
        "Product has been made: " + "\n" + prodToProduce + "\nAmount: " + prodQuantity);
  }
}
