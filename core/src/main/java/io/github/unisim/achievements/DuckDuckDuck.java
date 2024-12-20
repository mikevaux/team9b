package io.github.unisim.achievements;

/**
 * __NEW: WHOLE CLASS__ Creates an instance of DuckDuckDuck, which requires the longboi statue to be placed during the
 * longboi event. The checks for this are performed in BuildManager every time a building is placed.
 */
public class DuckDuckDuck extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;

  public DuckDuckDuck() {
    super("Achievement: Duck..Duck..Duck??!", "Placed the Longboi statue", "achievements/duck.png");
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
    DuckDuckDuck.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    DuckDuckDuck.alreadyDisplayed = alreadyDisplayed;
  }
}
