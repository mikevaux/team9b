package io.github.unisim.achievements;

/**
 * Creates an instance of OnFire, which requires a burnt building to be rebuilt.
 */
public class OnFire extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;
  private static String buildingToBuild = "";

  public OnFire() {
    super("On Fire!", "Rebuild a burnt building.", "achievements/flame.png");
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
    OnFire.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    OnFire.alreadyDisplayed = alreadyDisplayed;
  }

  public static void setBuildingToBuild(String building){
    buildingToBuild = building;
  }

  /**
   * Checks if the newly placed building is the same as the building that caught fire.
   *
   * @param buildingName the name of the building that was jus placed
   */
  public static void checkBuilding(String buildingName){
    if (buildingName == buildingToBuild){
      setDisplay(true);
    }
  }

}
