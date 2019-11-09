package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     Date: 10/14/19
 */
public interface ScreenSpec {
  // Each method is implicitly public, so the access modifier is not needed.

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
