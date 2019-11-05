package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 11/04/19
 */
public class Screen implements ScreenSpec {

  private String resolution;
  private int refreshrate;
  private int responsetime;

  public Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  public String getResolution() {
    return resolution;
  }

  public int getRefreshRate() {

    return refreshrate;
  }

  public int getResponseTime() {

    return responsetime;
  }

  public void setResoulution(String resolution) {
    this.resolution = resolution;
  }

  public void setRefreshRate(int refreshrate) {
    this.refreshrate = refreshrate;
  }

  public void setResponseTime(int responsetime) {
    this.responsetime = responsetime;
  }

  public String toString() {
    return "Resolution: " + getResolution() +
        "\nRefresh Rate: " + getRefreshRate() +
        "\nResponse Time: " + getResponseTime();
  }
}