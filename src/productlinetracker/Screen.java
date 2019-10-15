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

  public void setResoulution(String resoulution) {
    this.resolution = resolution;
  }

  public void setRefreshrate(int refreshrate) {
    this.refreshrate = refreshrate;
  }

  public void setResponsetime(int responsetime) {
    this.responsetime = responsetime;
  }

  public String toString() {
    return "Resolution: " + getResolution() +
        "\nRefresh Rate: " + getRefreshRate() +
        "\nResponse Time: " + getResponseTime();
  }
}