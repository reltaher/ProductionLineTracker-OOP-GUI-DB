package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 10/07/19
 */
public abstract class Product implements Item {
  private int id;
  private String name;
  private ItemType type;
  private String manufacturer;


  public Product(String name, ItemType type, String manufacturer) {
    this.name = name;
    this.type = type;
    this.manufacturer = manufacturer;
  }

  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public ItemType getType() {
    return type;
  }

  public void setType(ItemType type) {
    this.type = type;
  }
}
