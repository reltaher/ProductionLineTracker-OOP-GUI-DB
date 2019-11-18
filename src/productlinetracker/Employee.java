package productlinetracker;

/**
 * The Employee class allows users to input their full name and create a user id of their first
 * name, a period, and then their surname, which will be an email address of their first initial and
 * last name.
 *
 * <p>Date: 11/15/19
 *
 * @author Ramzy El-Taher
 */
public class Employee {
  private StringBuilder name;
  private String username;
  private String password;
  private String email;

  public Employee(String name, String password) {
    // use checkName() to check if name contains a space
    if (checkName()) {
      // if name contains a space, call setUsername and setEmail
      // set the name to both methods that were called
      setUsername(name);
      setEmail(name);

    } else { // if name does not contain a space
      // set username to "default"
      this.username = "default";
      // set email to "user@oracleacademy.Test"
      this.email = "user@oracleacademy.Test";
    }
    // -----------------------------------------

    // call the method isValidPassword()

    // use the method to check if the password is valid

    // if the password is valid, the password field is set to the supplied password

    // else, the password field gets set to "pw".

  }

  private void setUsername(String name) {
    // set username field to first initial of the first name + last name (all lowercase)
    this.username =
        name.substring(0, 2).toLowerCase() + name.substring(name.indexOf(" ")).toLowerCase();
  }

  private boolean checkName() {
    return name.toString().contains(" ");
  }

  private void setEmail(String name) {
    /* set email field to first name, then a period, then the last name (all lowercase), then
    @oracleacademy.Test at the end.
     */
    this.email =
        name.substring(0, name.indexOf(" ")).toLowerCase()
            + "."
            + name.substring(name.indexOf(" ")).toLowerCase()
            + "@oracleacademy.Test";
  }

  private boolean isValidPassword() {
    if (!password.matches("[A-Za-z0-9]")) {
      return false;
    }
    return true;
    }
}
