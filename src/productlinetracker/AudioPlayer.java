package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the Main class, where the program is launched and executed. The
 *     program is checked with CheckStyle using Google Checks, which uses the p tag.
 *     <p>Date: 10/07/19
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

  /**
   * Constructor that takes in the Product's name, manufacturer, Audio Specification, and media
   * type. It takes its parent's constructor and also sets up the media type to AUDIO.
   *
   * @param name The String variable that stores the Product's name.
   * @param manufacturer The String variable that stores the Product's manufacturer.
   * @param audioSpecification The String variable that stores the Product's Audio Specification.
   * @param mediaType The String variable that stores the Product's Media Type.
   */
  AudioPlayer(String name, String manufacturer, String audioSpecification, String mediaType) {
    super(name, ItemType.AUDIO, manufacturer);
    this.audioSpecification = audioSpecification;
    this.mediaType = mediaType;
  }

  /**
   * Method that returns the superclass's toString method, which will display the information to the
   * console.
   *
   * @return the superclass's toString method, and also adds additional rows for supported audio
   *     formats and supported playlist formats
   */
  public String toString() {
    return super.toString() + "\nAudio Spec: " + audioSpecification + "\nMedia Type: " + mediaType;
  }

  /** Method from the MultimediaControl Interface to display the "play" action to the console. */
  public void play() {
    System.out.println("Playing...");
  }

  /** Method from the MultimediaControl Interface to display the "stop" action to the console. */
  public void stop() {
    System.out.println("Stopping...");
  }

  /**
   * Method from the MultimediaControl Interface to display the "previous" action to the console.
   */
  public void previous() {
    System.out.println("Going to previous selection...");
  }

  /** Method from the MultimediaControl Interface to display the "next" action to the console. */
  public void next() {
    System.out.println("Going to next selection...");
  }

  /**
   * Accessor for audioSpecification
   *
   * @return audioSpecification, the private String field.
   */
  @SuppressWarnings("unused")
  public String getAudioSpecification() {
    return audioSpecification;
  }

  /**
   * Mutator for audioSpecification
   *
   * @param audioSpecification, the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setAudioSpecification(String audioSpecification) {
    this.audioSpecification = audioSpecification;
  }

  /**
   * Accessor for mediaType
   *
   * @return mediaType, the private String field.
   */
  @SuppressWarnings("unused")
  public String getMediaType() {
    return mediaType;
  }

  /**
   * Mutator for mediaType
   *
   * @param mediaType, the String variable that the private String field is assigned to.
   */
  @SuppressWarnings("unused")
  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }
}
