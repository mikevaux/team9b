package io.github.unisim.achievements;

/**
 * __NEW: WHOLE CLASS__
 */
public class Overachiever extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;
  private final int noOfAchievements = 7;

  /**
   * Creates an instance of Overachiever, which requires you to complete all other achievements.
   */
  public Overachiever() {
    super("Achievement: Overachiever!", "Complete all other achievements", "achievements/overachiever.png");
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
    Overachiever.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    Overachiever.alreadyDisplayed = alreadyDisplayed;
  }

  /**
   * Runs once every render call, checking if overachiever is now complete
   */
  public void checkOverachiever() {
    if (AchievementsHandler.getAchievementsGained() == noOfAchievements){
      Overachiever.setDisplay(true);
    }
  }
}
