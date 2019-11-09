package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the ScreenSpec interface, where a Product's screen resolution,
 *     refresh rate, and response time are obtained.
 *     <p>Date: 10/14/19
 */
public interface ScreenSpec {
  // Each method is implicitly public, so the access modifier is not needed.

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
