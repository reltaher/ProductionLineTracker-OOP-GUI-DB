package productlinetracker;

/**
 * The Item interface implements the methods of the item's name, manufacturer, and ID to the classes
 * that utilizes them.
 *
 * <p>Date: 10/07/19
 *
 * @author Ramzy El-Taher
 */
public interface Item {
  // Each method is implicitly public, so the access modifier is not needed.

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
