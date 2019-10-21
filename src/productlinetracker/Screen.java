package productlinetracker;

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