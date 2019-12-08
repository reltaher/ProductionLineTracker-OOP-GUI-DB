package productlinetracker;

/**
 * The Screen class is where the Product's screen information is made.
 *
 * <p>Date: 11/04/19
 *
 * @author Ramzy El-Taher
 */
public class Screen implements ScreenSpec {

  private String resolution;
  private int refreshRate;
  private int responseTime;

  /**
   * Constructor to use when creating a Screen object.
   *
   * @param resolution   The Screen's resolution stored as a String variable.
   * @param refreshRate  The Screen's refresh rate stored as an int variable.
   * @param responseTime The Screen's response time stored as an int variable.
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Accessor for resolution.
   *
   * @return resolution, the private String field.
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Accessor for refreshRate.
   *
   * @return refreshRate, the private int field.
   */
  public int getRefreshRate() {

    return refreshRate;
  }

  /**
   * Accessor for responseTime.
   *
   * @return responseTime, the private int field.
   */
  public int getResponseTime() {

    return responseTime;
  }

  /**
   * Mutator for resolution.
   *
   * @param resolution the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  /**
   * Mutator for refreshRate.
   *
   * @param refreshRate the int variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setRefreshRate(int refreshRate) {
    this.refreshRate = refreshRate;
  }

  /**
   * Mutator for responseTime.
   *
   * @param responseTime the int variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setResponseTime(int responseTime) {
    this.responseTime = responseTime;
  }

  /**
   * Method that returns the Screen's information as a String.
   *
   * @return resolution, which is a private String variable, refreshRate, which is a private int
   *        variable, and responseTime, which is a private int variable.
   */
  public String toString() {
    return "Resolution: "
        + getResolution()
        + "\nRefresh Rate: "
        + getRefreshRate()
        + "\nResponse Time: "
        + getResponseTime();
  }
}
