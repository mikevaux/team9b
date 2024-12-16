package io.github.unisim.achievements;

import io.github.unisim.building.BuildingType;

import java.util.Map;

/**
 * __NEW: WHOLE CLASS__ Creates an instance of HowDidWeGetHere, which requires one of each building type to be placed.
 */
public class HowDidWeGetHere extends Achievement{
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;

  public HowDidWeGetHere() {
    super("How Did We Get Here?", "Have 1 of each building type\nplaced at the same time.", "achievements/bucket.png");
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
    HowDidWeGetHere.display = !isAlreadyDisplayed() && display;
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    HowDidWeGetHere.alreadyDisplayed = alreadyDisplayed;
  }

  /**
   * Checks if all 4 non-event building types have been placed.
   *
   * @param buildingCounts a hashmap of the placed building types, and the number of each
   */
  public static void checkBuildings(Map<BuildingType, Integer> buildingCounts){
    if (buildingCounts.size() == 4){
      setDisplay(true);
    }
  }

}
