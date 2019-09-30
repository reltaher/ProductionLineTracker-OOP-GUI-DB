package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the itemType enum class, where the the enums for each item types are
 *     created, along with a String value of code which each enum carries with them.
 *     <p>Date: 09/30/19
 */
public enum ItemType {
  AU("Audio"), VI("Visual"), AM("AudioMobile"), VM("VisualMobile");

  private String code;

  /**
   * Sets a String parameter for the enum class. Each enum will require a String value, in which the
   * value will be the item type's code.
   *
   * @param code, which are the String values of each enum.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Getter method for code
   *
   * @return String, which returns the value "code".
   */
  public String getCode() {
    return code;
  }
}
