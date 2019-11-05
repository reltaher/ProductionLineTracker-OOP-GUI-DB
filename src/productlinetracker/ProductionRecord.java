package productlinetracker;

import java.util.Date;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 10/14/19
 */
public class ProductionRecord {

  // int that is unique for every item produced and gets auto incremented by the database
  private int productionNumber;
  // int that corresponds to the productID from the Product table / class
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  /**
   * Constructor called when user records production from the user interface
   * @param productID
   */
  public ProductionRecord(int productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    //caller makes to the original dateProduced object
    this.dateProduced = dateProduced;
  }

  public ProductionRecord(Product aProduct, int itemCount) {
    this.serialNumber = aProduct.getManufacturer().substring(0,3) + aProduct.getType().getCode() +
        String.format("%05d", itemCount);
    this.productionNumber = 0;
    this.productID = 0;
    this.dateProduced = new Date();
  }

  public String toString() {
    return "Production Num: " + productionNumber +
        " Product ID: " + productID +
        " Serial Num: " + serialNumber + " Date: " + dateProduced;
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getDateProduced() {
    return dateProduced;
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

}
