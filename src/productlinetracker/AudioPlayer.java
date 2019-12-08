package productlinetracker;

/**
 * The AudioPlayer class is where the program captures the details of an audio player.
 *
 * <p>Date: 10/07/19
 *
 * @author Ramzy El-Taher
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * Constructor that takes in the Product's name, manufacturer, Audio Specification, and media
   * type. It takes its parent's constructor and also sets up the media type to AUDIO.
   *
   * @param name                     The String variable that stores the Product's name.
   * @param manufacturer             The String variable that stores the Product's manufacturer.
   * @param supportedAudioFormats    The String variable that stores the Product's Audio
   *                                 Specification.
   * @param supportedPlaylistFormats The String variable that stores the Product's Media Type.
   */
  AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, ItemType.AUDIO, manufacturer);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Method that returns the superclass's toString method, which will display the information to the
   * console.
   *
   * @return the superclass's toString method, and also adds additional rows for supported audio
   *        formats and supported playlist formats
   */
  public String toString() {
    return super.toString()
        + "\nAudio Spec: "
        + supportedAudioFormats
        + "\nMedia Type: "
        + supportedPlaylistFormats;
  }

  /**
   * Method from the MultimediaControl Interface to display the "play" action to the console.
   */
  public void play() {
    System.out.println("Playing...");
  }

  /**
   * Method from the MultimediaControl Interface to display the "stop" action to the console.
   */
  public void stop() {
    System.out.println("Stopping...");
  }

  /**
   * Method from the MultimediaControl Interface to display the "previous" action to the console.
   */
  public void previous() {
    System.out.println("Going to previous selection...");
  }

  /**
   * Method from the MultimediaControl Interface to display the "next" action to the console.
   */
  public void next() {
    System.out.println("Going to next selection...");
  }

  /**
   * Accessor for supportedAudioFormats.
   *
   * @return audioSpecification, the private String field.
   */
  @SuppressWarnings("unused")
  public String getSupportedAudioFormats() {
    return supportedAudioFormats;
  }

  /**
   * Mutator for supportedAudioFormats.
   *
   * @param supportedAudioFormats the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setSupportedAudioFormats(String supportedAudioFormats) {
    this.supportedAudioFormats = supportedAudioFormats;
  }

  /**
   * Accessor for supportedPlaylistFormats.
   *
   * @return mediaType, the private String field.
   */
  @SuppressWarnings("unused")
  public String getSupportedPlaylistFormats() {
    return supportedPlaylistFormats;
  }

  /**
   * Mutator for supportedPlaylistFormats.
   *
   * @param supportedPlaylistFormats the String variable that the private String field is assigned
   *                                 to.
   */
  @SuppressWarnings("unused")
  public void setSupportedPlaylistFormats(String supportedPlaylistFormats) {
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }
}
