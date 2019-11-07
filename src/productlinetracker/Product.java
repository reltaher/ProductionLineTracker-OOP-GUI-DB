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

  /**
   * Constructor for a Product object.
   *
   * @param name The Product's name, stored as a String variable.
   * @param type The Product's type, stored as an ItemType enum.
   * @param manufacturer The Product's manufacturer, stored as a String variable.
   */
  public Product(String name, ItemType type, String manufacturer) {
    this.name = name;
    this.type = type;
    this.manufacturer = manufacturer;
  }

  /**
   * Method that return's the Product's information as a String
   *
   * @return name, which is a private String field, manufacturer, which is a private String field,
   *     and type, which is a private ItemType enum.
   */
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type.getCode();
  }

  /**
   * Accessor for id.
   *
   * @return id, the private int field.
   */
  public int getId() {
    return id;
  }

  /**
   * Mutator for id.
   *
   * @param id, the int variable that the private int field is assigned to.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Accessor for name.
   *
   * @return name, the private String field.
   */
  public String getName() {
    return name;
  }

  /**
   * Mutator for name.
   *
   * @param name, the String variable that the private String field is assigned to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Accessor for manufacturer.
   *
   * @return manufacturer, the private String field.
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Mutator for manufacturer.
   *
   * @param manufacturer, the String field that the private String field is assigned to.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Accessor for type.
   *
   * @return type, the private ItemType enum.
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Mutator for type.
   *
   * @param type, the ItemType enum that the private ItemType enum is assigned to.
   */
  public void setType(ItemType type) {
    this.type = type;
  }
}
