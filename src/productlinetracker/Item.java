package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Item interface, which will force all classes to implement the
 *     functions made in here.
 *     <p>Date: 10/07/19
 */
public interface Item {
  // Each method is implicitly public, so the access modifier is not needed.

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
