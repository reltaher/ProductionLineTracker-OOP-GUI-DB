package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 10/14/19
 */
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
