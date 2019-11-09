package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *
 *     The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the itemType enum class, where the the enums for each item types are
 *     created, along with a String value of code which each enum carries with them.
 *
 *     Date: 09/30/19
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");

  private String code;

  /**
   * Sets a String parameter for the enums. Each enum will require a String value, in which the
   * value will be the item type's code.
   *
   * @param code which are the String values of each enum.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Accessor for code.
   *
   * @return String, which returns the value "code".
   */
  public String getCode() {
    return code;
  }

  /**
   * Method that returns an ItemType constant value if the code for the value is equal to the enum.
   * Credit: https://stackoverflow.com/questions/12639791/what-is-the-reason-for-java-lang-
   * illegalargumentexception-no-enum-const-class-e
   *
   * @param code The string variable that identifies the ItemType enum values.
   * @return types which are the ItemType enum values. If an enum type does not exist then the
   *     method will throw a new IllegalArgumentException, stating that the item type code does not
   *     exist.
   */
  public static ItemType fromString(String code) {
    // For each loop which loops through each Item Type enum.
    for (ItemType types : ItemType.values()) {
      // Compares the code (String value) from ItemType with the code parameter.
      if (types.code.equalsIgnoreCase(code)) {
        // Returns the ItemType enum if the code from ItemType matches the code from String.
        return types;
      }
    }
    // If the item type does not exist, an IllegalArgumentException is thrown.
    throw new IllegalArgumentException(code + " is not a valid item type.");
  }
}
