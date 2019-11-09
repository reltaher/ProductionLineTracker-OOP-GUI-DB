package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Widget Class, which is a class used to create Product objects.
 *     <p>Date: 10/01/19
 */
class Widget extends Product {

  /**
   * Constructor which is used to create Product objects in the form of a Widget. The constructor
   * inherits the fields from the Product class, and uses those fields to create objects.
   *
   * @param name The Product's name.
   * @param type The Product's type.
   * @param manufacturer The Product's manufacturer.
   */
  Widget(String name, ItemType type, String manufacturer) {
    super(name, type, manufacturer);
  }
}
