package productlinetracker;

public class AudioPlayer extends Product implements MultimediaControl {

  private String audioSpecification;
  private String mediaType;

  public AudioPlayer(String name, String manufacturer, String audioSpecification, String mediaType) {
    super(name, ItemType.AUDIO, manufacturer);
    this.audioSpecification = audioSpecification;
    this.mediaType = mediaType;
  }

  public String toString() {
    return super.toString() + "\nAudio Spec: " + audioSpecification + "\nMedia Type: " + mediaType;
  }

  public void play() {
    System.out.println("Playing...");
  }

  public void stop() {
    System.out.println("Stopping...");
  }

  public void previous() {
    System.out.println("Going to previous selection...");
  }

  public void next() {
    System.out.println("Going to next selection...");
  }

  public String getAudioSpecification() {
    return audioSpecification;
  }

  public void setAudioSpecification(String audioSpecification) {
    this.audioSpecification = audioSpecification;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }
}
