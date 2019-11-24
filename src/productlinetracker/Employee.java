package productlinetracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        this.name = new StringBuilder(name);
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
        this.password = password;
        // call the method isValidPassword()
        if (isValidPassword()) {
            // if the password is valid, the password field is set to the supplied password
            this.password = password;
        } else {
            // else, the password field gets set to "pw".
            this.password = "pw";
        }

    }

    public String toString() {
        return "Name: " + this.name + "\nUsername: " + this.username + "\nEmail: " + this.email +
                "\nInitial Password: " + this.password;
    }

    private void setUsername(String name) {
        // set username field to first initial of the first name + last name (all lowercase)
        this.username =
                name.substring(0, 1).toLowerCase() + name.substring(name.indexOf(" ")).toLowerCase().replace(" ", "");
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
                        + name.substring(name.indexOf(" ")).toLowerCase().replace(" ", "")
                        + "@oracleacademy.Test";
    }

    private boolean isValidPassword() {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern specialChar = Pattern.compile("\\W");
        Matcher matchUpper = uppercase.matcher(password);
        Matcher matchLower = lowercase.matcher(password);
        Matcher matchSpecial = specialChar.matcher(password);
        return matchUpper.find() && matchLower.find() && matchSpecial.find();
    }

}
