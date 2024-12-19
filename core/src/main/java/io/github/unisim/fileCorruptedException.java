package io.github.unisim;

/**
 * __NEW: WHOLE CLASS__ checked exception that ensures the file is in the right format.
 * If incorrect format, only the Home button is displayed
 *
 * File structure should be:
 * line1: player 1, score 1, player 2, score 2, player3, score 3, player 4, score 4, player 5, score 5
 * line2: end
 *
 */
public class fileCorruptedException extends Exception{

  public fileCorruptedException(){
    super("file format is not as expected");
  }

}
