package io.github.unisim.achievements;

/**
 * __NEW: WHOLE CLASS__ Creates an instance of PressPlayToStart, which requires the user to press play to start.
 * The boolean display is set to true when start is clicked.
 */
public class PressStartToPlay extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;

  public PressStartToPlay() {
    super("Press Start To Play.", "The Journey Begins Here...", "achievements/playButton.png");
  }

  public static boolean isDisplay() {
    return display;
  }

  /**
   * Sets static boolean display. can only be true if the achievements hasn't been displayed yet.
   *
   * @param display the value the boolean is being attempted to be set to
   */
  public static void setDisplay(boolean display) {
    PressStartToPlay.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    PressStartToPlay.alreadyDisplayed = alreadyDisplayed;
  }
}
