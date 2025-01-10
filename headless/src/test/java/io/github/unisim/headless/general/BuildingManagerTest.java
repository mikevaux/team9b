package io.github.unisim.headless.general;

import io.github.unisim.BuildingManager;
import io.github.unisim.building.*;
import io.github.unisim.events.WinterHolidays;
import io.github.unisim.satisfaction.SatisfactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingManagerTest {
  private BuildingManager buildingManager;

  @BeforeEach
  public void boot() {
    buildingManager = new BuildingManager(null);
    buildingManager.registerSatisfactionHandler(new SatisfactionHandler(buildingManager, new WinterHolidays()));
  }

  /**
   * Test 1.2.1.
   */
  @Test
  public void testUpdateCounters() {
    BuildingType[] countTypes = new BuildingType[]{
      BuildingType.RECREATION,
      BuildingType.LEARNING,
      BuildingType.SLEEPING,
      BuildingType.EATING,
    };

    // Test that all counts are zero initially
    for (BuildingType type : countTypes) {
      assertEquals(0, buildingManager.getBuildingCount(type), "Initial count of all types should be 0.");
    }

    // Test that each of the 4 counts is being incremented the first time
    batchBuild();
    for (BuildingType type : countTypes) {
      assertEquals(1, buildingManager.getBuildingCount(type));
    }

    // Test that each of the 4 counts is being incremented the second time
    batchBuild();
    for (BuildingType type : countTypes) {
      assertEquals(2, buildingManager.getBuildingCount(type));
    }

    // Test that there is no counting of Event type buildings
    assertEquals(0, buildingManager.getBuildingCount(BuildingType.EVENT));
  }

  /**
   * Test 1.2.2.
   */
  @Test
  public void testDecrementCounters() {
    EatingBuilding eatingBuilding = new EatingBuilding();

    buildingManager.buildBuilding(eatingBuilding);
    assertEquals(1, buildingManager.getBuildingCount(eatingBuilding.getType()));
    buildingManager.decrementCounter(eatingBuilding);
    assertEquals(0, buildingManager.getBuildingCount(eatingBuilding.getType()));
  }

  /**
   * Test 1.2.3.
   */
  @Test
  public void testFilterBuildings() {
    ArrayList<Building> built = batchBuild();

    // filterBuildings should filter out event type buildings, leaving one less than was built
    assertEquals(built.size() - 1, buildingManager.filterBuildings().size());
  }

  /**
   * Test 1.2.4
   */
  @Test
  public void testGetNumberOf() {
    BuildingType[] allTypes = new BuildingType[]{
      BuildingType.RECREATION,
      BuildingType.LEARNING,
      BuildingType.SLEEPING,
      BuildingType.EATING,
      BuildingType.EVENT
    };

    for (BuildingType type : allTypes) {
      assertEquals(0, buildingManager.getNumberOf(type));
    }

    batchBuild();

    for (BuildingType type : allTypes) {
      assertEquals(1, buildingManager.getNumberOf(type));
    }
  }

  /**
   * Test 1.2.5.
   */
  @Test
  public void testLongboiStatuePlaced() {
    assertFalse(buildingManager.longboiStatuePlaced());
    buildingManager.buildBuilding(new LongBoiStatue());
    assertTrue(buildingManager.longboiStatuePlaced());
  }

  /**
   * Builds one building of each {@link BuildingType}.
   */
  private ArrayList<Building> batchBuild() {
    ArrayList<Building> buildings = new ArrayList<>();

    buildings.add(new EatingBuilding());
    buildings.add(new LearningBuilding());
    buildings.add(new BasketballCourt());
    buildings.add(new SleepingBuilding());
    buildings.add(new LongBoiStatue());

    for (Building building : buildings) {
      buildingManager.buildBuilding(building);
    }

    return buildings;
  }
}
