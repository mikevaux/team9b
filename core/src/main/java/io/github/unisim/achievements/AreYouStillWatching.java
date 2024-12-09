package io.github.unisim.achievements;

public class AreYouStillWatching extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;
  private static float counter = 0f;

  /**
   * Creates an instance of AreYouStillWatching, which requires you not to place a building for more than a minute.
   */
  public AreYouStillWatching() {
    super("Are You Still Watching?", "1 minute without any buildings placed", "achievements/notflix.png");
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
    AreYouStillWatching.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    AreYouStillWatching.alreadyDisplayed = alreadyDisplayed;
  }

  /**
   * Resets the counter. Called when a building is placed.
   */
  public static void resetCounter(){
    counter = 0f;
  }

  /**
   * Runs once every render call, checking if AreYouStillWatching is now complete.
   *
   * @param delta the time since the last render call
   */
  public void checkAreYouStillWatching(float delta){
    counter += delta;
    if (counter > 60f){
      setDisplay(true);
    }
  }

}
