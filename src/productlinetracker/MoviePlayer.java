package productlinetracker;

public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, ItemType.VISUAL, manufacturer);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  public String toString() {
    return super.toString() + "\nScreen: " + screen + "\nMonitor Type: " + monitorType;
  }

  public void play() {

  }

  public void stop() {

  }

  public void previous() {

  }

  public void next() {

  }

  public Screen getScreen() {
    return screen;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public MonitorType getMonitorType() {
    return monitorType;
  }

  public void setMonitorType(MonitorType monitorType) {
    this.monitorType = monitorType;
  }
}
