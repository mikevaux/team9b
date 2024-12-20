package io.github.unisim.achievements;

/**
 * __NEW: WHOLE CLASS__ Creates an instance of DoYouSmellCarrots, which requires you to gain satisfaction during the
 * winter holidays.
 */
public class DoYouSmellCarrots extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;

  public DoYouSmellCarrots() {
    super("Achievement: Do You Smell Carrots?.", "Increase satisfaction during the winter holiday event", "achievements/snowman.png");
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
    DoYouSmellCarrots.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    DoYouSmellCarrots.alreadyDisplayed = alreadyDisplayed;
  }

  public void checkDoYouSmellCarrots(){

  }

}
