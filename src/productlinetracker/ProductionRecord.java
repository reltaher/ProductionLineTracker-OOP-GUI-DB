package productlinetracker;

import java.util.Date;

/**
 * The program is a software made in JavaFX where it tracks the number and types of products being
 * made. This file is the ProductionRecord class, which is used to store information about the
 * product being made, including generating a unique serial number for each product produced.
 *
 * <p>Date: 10/14/19
 *
 * @author Ramzy El-Taher
 */
public class ProductionRecord {

  // int that is unique for every item produced and gets auto incremented by the database
  private int productionNumber;
  // int that corresponds to the productID from the Product table / class
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  /**
   * Constructor called when user records production from the user interface.
   *
   * @param productID the int variable that the productID field is assigned to.
   */
  public ProductionRecord(int productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Constructor used for creating ProductionRecord objects that are stored to the database.
   *
   * @param productionNumber the int variable that the productionNumber field is assigned to.
   * @param productID the int variable that the productID field is assigned to.
   * @param serialNumber the String variable that the serialNumber field is assigned to.
   * @param dateProduced the date variable that the dateProduced field is assigned to.
   */
  public ProductionRecord(
      int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Constructor that generates a unique serial number for each product produced.
   *
   * @param product a Product object that obtained the manufacturer and type for the serial number.
   * @param itemCount an int variable that is used to be incremented for every product produced.
   */
  public ProductionRecord(Product product, int itemCount) {
    this.serialNumber =
        product.getManufacturer().substring(0, 3)
            + product.getType().getCode()
            + String.format("%05d", itemCount);
    this.productionNumber = 0;
    this.productID = 0;
    this.dateProduced = new Date();
  }

  /**
   * Method that returns the Product information in the form of a string.
   *
   * @return the product's productionNumber, productID, serialNumber, and dateProduced.
   */
  public String toString() {
    return "Production Num: "
        + productionNumber
        + " Product ID: "
        + productID
        + " Serial Num: "
        + serialNumber
        + " Date: "
        + dateProduced;
  }

  /**
   * Accessor for productionNumber.
   *
   * @return productionNumber, the private String field.
   */
  @SuppressWarnings("unused")
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Mutator for productionNumber.
   *
   * @param productionNumber the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Accessor for productID.
   *
   * @return productID, the private String field.
   */
  int getProductID() {
    return productID;
  }

  /**
   * Mutator for productID.
   *
   * @param productID the int variable that the private String field is assigned to.
   */
  void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Accessor for serialNumber.
   *
   * @return serialNumber, the private String field.
   */
  String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Mutator for serialNumber.
   *
   * @param serialNumber the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Accessor for dateProduced.
   *
   * @return dateProduced, the private String field.
   */
  Date getDateProduced() {
    return dateProduced;
  }

  /**
   * Mutator for dateProduced.
   *
   * @param dateProduced the Date variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }
}
