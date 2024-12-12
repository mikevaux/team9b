package io.github.unisim.satisfaction;

import io.github.unisim.building.Building;
import io.github.unisim.BuildingManager;
import io.github.unisim.building.BuildingType;
import io.github.unisim.ui.InfoBar;

import java.util.ArrayList;

public class SatisfactionHandler {
  private InfoBar bar;
  private int satisfaction;
  private BuildingManager buildingManager;

  public SatisfactionHandler(InfoBar bar, BuildingManager buildingManager){
    this.bar = bar;
    this.buildingManager = buildingManager;
    this.satisfaction = 0;
  }

  public int getSatisfaction() {
    return satisfaction;
  }

  /**
   * Calculates the satisfaction building is worth based on its size.
   *
   * @param building the building to calculate the satisfaction score of
   *
   * @return the satisfaction the building is worth as an int
   */
  private int calculateBuildingScore(Building building) {
    int satisfactionToAdd = 0;
    if (building.getName() == "Canteen" || building.getName() == "Basketball Court") {
      satisfactionToAdd += 15; //smaller buildings
    } else {
      satisfactionToAdd += 30; //larger buildings
    }
    return satisfactionToAdd;
  }

  /**
   * Calculates the bonus satisfaction to be awarded at the end of the game based off of the ratios of building types.
   *
   * @return the bonus to be added to the satisfaction as an int
   */
  private int calculateBonus(){
    int satisfactionToAdd = 0;
    final int smallBonus = 100;
    final int largeBonus = 200;
    int sleepingNum = buildingManager.getNumberOf(BuildingType.SLEEPING);
    int learningNum = buildingManager.getNumberOf(BuildingType.LEARNING);
    int recreationNum = buildingManager.getNumberOf(BuildingType.RECREATION);
    int eatingNum = buildingManager.getNumberOf(BuildingType.EATING);
    boolean allTrue = true;
    //are there at least twice as many recreation buildings as food buildings?
    if (recreationNum >= (2 * eatingNum)){
      satisfactionToAdd += smallBonus;
    }else{
      allTrue = false;
    }
    //are there at least twice as many learning buildings as recreation buildings?
    if (learningNum >= (2 * recreationNum)){
      satisfactionToAdd += smallBonus;
    }else{
      allTrue = false;
    }
    //are there at least twice as many sleeping buildings as learning buildings?
    if (sleepingNum >= (2 * learningNum)){
      satisfactionToAdd += smallBonus;
    }else{
      allTrue = false;
    }
    //have all bonuses been met?
    if (allTrue){
      satisfactionToAdd += largeBonus;
    }
    return satisfactionToAdd;
  }

  /**
   * Calculates the total satisfaction gained from the base values each building has.
   *
   * @return the total base satisfaction as an int
   */
  private int calculateBasicSatisfaction(){
    int basicSatisfaction = 0;
    for(Building building: buildingManager.filterBuildings()){
      basicSatisfaction += calculateBuildingScore(building);
    }
    return basicSatisfaction;
  }

  /**
   * Calculates the satisfaction to be displayed in Game.
   * This includes the base satisfaction and the proximity satisfaction.
   *
   * @return the satisfaction to be displayed in game as an int.
   */
  private int calculateGameSatisfaction(){
    int gameSatisfaction = 0;
    gameSatisfaction += calculateBasicSatisfaction();
    gameSatisfaction += calculateProximityBonus();
    return gameSatisfaction;
  }

  /**
   * Updates the value of the satisfaction on the information bar.
   * Called once every render cycle.
   */
  public void updateSatisfaction(){
    satisfaction = calculateGameSatisfaction();
    bar.updateSatisfactionLabel(String.valueOf(satisfaction));
  }

  /**
   * Calculates the final value of the satisfaction to be displayed after the game.
   * This includes the base, proximity, bonus, and event bonus satisfactions.
   */
  public void updatePostGameSatisfaction(){
    int postGameSatisfaction = 0;
    postGameSatisfaction += calculateBasicSatisfaction(); //add base building satisfaction
    postGameSatisfaction += calculateProximityBonus(); //add proximity based satisfaction bonuses
    postGameSatisfaction += calculateBonus(); //add bonuses
    //check for and add the longboi statue bonus
    if (buildingManager.longboiStatuePlaced()){
      postGameSatisfaction *= 1.5;
    }
    satisfaction = postGameSatisfaction;
  }

  /**
   * Calculates the total value of the proximity based satisfaction.
   * This is calculated based on the distance from each accommodation to the nearest building of each other type.
   *
   * @return the total proximity satisfaction score to be added to the total satisfaction
   */
  public int calculateProximityBonus(){
    ArrayList<Building> accommodationList = buildingManager.getTypeBuildings(BuildingType.SLEEPING);
    ArrayList<Building> recreationList = buildingManager.getTypeBuildings(BuildingType.RECREATION);
    ArrayList<Building> learningList = buildingManager.getTypeBuildings(BuildingType.LEARNING);
    ArrayList<Building> foodList = buildingManager.getTypeBuildings(BuildingType.EATING);
    int satisfactionBonus = 0;
    for (Building accomodationBuilding: accommodationList) {
      //learning bonus
      int minDistLearning = getClosestDistance(accomodationBuilding, learningList);
      satisfactionBonus += bonusSize(minDistLearning, true);
      //recreation bonus
      int minDistRecreation = getClosestDistance(accomodationBuilding, recreationList);
      satisfactionBonus += bonusSize(minDistRecreation, false);
      //food bonus
      int minDistFood = getClosestDistance(accomodationBuilding, foodList);
      satisfactionBonus += bonusSize(minDistFood, false);
    }
    return satisfactionBonus;
  }

  /**
   * Calculates the smallest distance between a chosen building, and all other buildings in a list.
   *
   * @param building1 the building to find the shortest distance from
   * @param buildingList the list of buildings to compare against building1
   *
   * @return the shortest distance from building 1 to any building in buildingList
   */
  private int getClosestDistance(Building building1, ArrayList<Building> buildingList){
    int minDistance = 10000; //arbitrarily large number such that any distances should be lesser
    for (Building building2: buildingList) {
      int currentDistance = calculateDistance(building1, building2);
      if (currentDistance < minDistance){
        minDistance = currentDistance;
      }
    }
    return minDistance;
  }

  /**
   * Calculates the distance between 2 buildings.
   * Distance is calculated using the supremum Metric which is defined as:
   * Distance between (x1, y1) and (x2, y2) = Max(|x1-x2|,|y1-y2|).
   *
   * @param building1 the first building
   * @param building2 the second building
   *
   * @return the distance between the first and second building
   */
  private int calculateDistance(Building building1, Building building2){
    int distance = Math.max(Math.abs(building1.getLocation().x - building2.getLocation().x),Math.abs(building1.getLocation().y - building2.getLocation().y));
    return distance;
  }

  /**
   * Calculates the size of the satisfaction bonus to be awarded bases off of the minimum distance.
   * This bonus is doubled if the other buildings type is learning.
   *
   * @param distance the distance between an accommodation building, and buildings of another type
   * @param isDoubled should the bonus be doubled
   *
   * @return the int value of the bonus
   */
  private int bonusSize(int distance, boolean isDoubled){
    int bonus = 0;
    if (distance <= 25){
      bonus = 25;
    }else if (distance <= 50){
      bonus = 10;
    }else if (distance <= 100){
      bonus = 5;
    }
    if (isDoubled){
      bonus *= 2;
    }
    return bonus;
  }
}
