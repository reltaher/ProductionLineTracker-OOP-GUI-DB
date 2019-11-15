package productlinetracker;

/**
 * The ScreenSpec interface is where a Product's screen resolution, refresh rate, and response time
 * are obtained.
 *
 * <p>Date: 10/14/19
 *
 * @author Ramzy El-Taher
 */
public interface ScreenSpec {
  // Each method is implicitly public, so the access modifier is not needed.

  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
