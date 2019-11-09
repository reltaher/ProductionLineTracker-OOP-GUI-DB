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
public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  /**
   * Constructor that contains a MoviePlayer's information when an object is created.
   *
   * @param name the variable that inherits its parents field.
   * @param manufacturer the variable that inherits its parents field.
   * @param screen the variable that its respective field is assigned to.
   * @param monitorType the variable that its respective field is assigned to.
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, ItemType.VISUAL, manufacturer);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Method that returns a MoviePlayer's information as a string.
   *
   * @return The MoviePlayer's screen and monitorType fields.
   */
  public String toString() {
    return super.toString() + "\nScreen: " + screen + "\nMonitor Type: " + monitorType;
  }

  /**
   * The method "play", implemented from the MultimediaControl interface.
   */
  public void play() {

  }

  /**
   * The method "stop", implemented from the MultimediaControl interface.
   */
  public void stop() {

  }

  /**
   * The method "previous", implemented from the MultimediaControl interface.
   */
  public void previous() {

  }

  /**
   * The method "next", implemented from the MultimediaControl interface.
   */
  public void next() {

  }

  /**
   * Accessor for screen.
   *
   * @return screen, a field in the MoviePlayer class.
   */
  @SuppressWarnings("unused")
  public Screen getScreen() {
    return screen;
  }

  /**
   * Mutator for screen.
   *
   * @param screen The variable that the screen field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  /**
   * Accessor for monitorType.
   *
   * @return monitorType, a field from the MoviePlayer class.
   */
  @SuppressWarnings("unused")
  public MonitorType getMonitorType() {
    return monitorType;
  }

  /**
   * Mutator for monitorType.
   *
   * @param monitorType the variable that the monitorType field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setMonitorType(MonitorType monitorType) {
    this.monitorType = monitorType;
  }
}
