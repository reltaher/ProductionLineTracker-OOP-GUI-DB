package productlinetracker;

/**
 * Represents the author of the program.
 *
 * @author Ramzy El-Taher
 *     <p>The program is a software made in JavaFX where it tracks the number and types of products
 *     being made. This file is the MultimediaControl interface, where the functions of an audio
 *     player and a movie player are obtained.
 *     <p>Date: 10/14/19
 */
public interface MultimediaControl {

  void play();

  void stop();

  void previous();

  void next();
}
