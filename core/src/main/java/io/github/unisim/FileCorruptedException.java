package io.github.unisim;

/**
 * __NEW: WHOLE CLASS__ checked exception that ensures the file is in the right format.
 * If incorrect format, an error message is displayed instead of the leaderboard.
 *
 * File structure should be:
 * line1: player 1,score 1,
 * Line2: player 2,score 2,
 * Line3: player 3,score 3,
 * Line4: player 4,score 4,
 * Line5: player 5,score 5,
 * Line6:
 *
 */
public class FileCorruptedException extends Exception{

  public FileCorruptedException(){
    super("an error occurred loading/writing to leaderboard.txt");
  }

}
