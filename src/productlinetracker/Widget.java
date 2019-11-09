package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     Date: 10/01/19
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
