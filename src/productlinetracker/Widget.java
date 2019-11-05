package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 10/01/19
 */
class Widget extends Product {

  Widget(String name, ItemType type, String manufacturer)
  {
    super(name, type, manufacturer);
  }
}
