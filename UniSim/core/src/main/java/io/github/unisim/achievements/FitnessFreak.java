package io.github.unisim.achievements;

import io.github.unisim.building.BuildingType;
import java.util.Map;

/**
 * Creates an instance of FitnessFreak, which requires a recreational building to be the first building placed.
 */
public class FitnessFreak extends Achievement {
  private static boolean display = false;
  private static boolean alreadyDisplayed = false;

  public FitnessFreak() {
    super("Fitness Freak!", "Place a recreation building as your first building", "achievements/flexArm.png");
  }

  public static boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }

  public static void setAlreadyDisplayed(boolean alreadyDisplayed) {
    FitnessFreak.alreadyDisplayed = alreadyDisplayed;
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
    FitnessFreak.display = !isAlreadyDisplayed() && display;
  }

  /**
   * Checks if the only building type placed is recreational.
   *
   * @param buildingCounts a hashmap of the placed building types, and the number of each
   */
  public static void checkBuildings(Map<BuildingType, Integer> buildingCounts){
    if (buildingCounts.size() == 1 && buildingCounts.containsKey(BuildingType.RECREATION)){
      setDisplay(true);
    }
  }

}
