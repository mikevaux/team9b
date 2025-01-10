package io.github.unisim.headless.general;

import io.github.unisim.BuildingManager;
import io.github.unisim.Point;
import io.github.unisim.building.*;
import io.github.unisim.events.WinterHolidays;
import io.github.unisim.satisfaction.SatisfactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test 1.8.x.
 */
public class SatisfactionHandlerTest extends AbstractGeneralTest {
  private BuildingManager buildingManager;
  private SatisfactionHandler satisfactionHandler;
  private final int smallBuildingScore = 15;
  private final int largeBuildingScore = 30;
  private final int smallBonus = 250;
  private final int largeBonus = 500;
  private final int smallProxBonus = 5;
  private final int mediumProxBonus = 10;
  private final int largeProxBonus =25;

  @BeforeEach
  public void boot() {
    buildingManager = new BuildingManager(null);
    satisfactionHandler = new SatisfactionHandler(buildingManager, new WinterHolidays());
  }

  /**
   * Test 1.8.1.
   */
  @Test
  public void testInitialSatisfaction() {
    assertEquals(0, satisfactionHandler.getGameSatisfaction(), "Initial satisfaction should be zero.");
  }

  /**
   * Test 1.8.2.
   */
  @Test
  public void testSmallBuildingSatisfaction() {
    //test one basketball court
    buildingManager.buildBuilding(new BasketballCourt());
    assertEquals(smallBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for one small building should have been 15");
    //test two basketball courts
    buildingManager.buildBuilding(new BasketballCourt());
    assertEquals(2*smallBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for two small buildings should have been 30");
    //test two basketball courts and a canteen
    buildingManager.buildBuilding(new EatingBuilding());
    assertEquals(3*smallBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for three small buildings should have been 45");
  }

  /**
   * Test 1.8.3.
   */
  @Test
  public void testLargeBuildingSatisfaction() {
    //test one stadium
    buildingManager.buildBuilding(new Stadium());
    assertEquals(largeBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for one large building should have been 30");
    //test two stadiums
    buildingManager.buildBuilding(new Stadium());
    assertEquals(2*largeBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for two large buildings should have been 60");
    //test two stadiums and a library
    buildingManager.buildBuilding(new LearningBuilding());
    assertEquals(3*largeBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for three large buildings should have been 90");
    //test two stadiums, a library, and an accommodation
    SleepingBuilding accommodation = new SleepingBuilding();
    accommodation.setLocation(new Point(101, 101));//prevent proximity bonus from having any effect
    buildingManager.buildBuilding(accommodation);
    assertEquals(4*largeBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for four large buildings should have been 120");
  }

  /**
   * Test 1.8.4.
   */
  @Test
  public void testBonus(){
    //check bonus not awarded when too few buildings
    for (int i = 0; i < 9; i++) {
      buildingManager.buildBuilding(new LearningBuilding());
    }
    assertEquals(9*largeBuildingScore, satisfactionHandler.getPostGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getPostGameSatisfaction() +
        ", satisfaction for nine libraries should have been 270");
    //check bonus not awarded when ratios wrong
    for (int i = 0; i < 5; i++) {
      buildingManager.buildBuilding(new Stadium());
    }
    assertEquals(14*largeBuildingScore, satisfactionHandler.getPostGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getPostGameSatisfaction() +
        ", satisfaction for nine libraries and 5 stadiums should have been 270");
    //check small bonus awarded when only one condition is met
    buildingManager.buildBuilding((new LearningBuilding()));
    assertEquals(15*largeBuildingScore + smallBonus, satisfactionHandler.getPostGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getPostGameSatisfaction() +
        ", satisfaction for ten libraries and 5 stadiums should have been 700");
    //check all bonuses awarded when both conditions met
    for (int i = 0; i < 10; i++) {
      SleepingBuilding accommodation = new SleepingBuilding();
      accommodation.setLocation(new Point(101, 101));//prevent proximity bonus from having any effect
      buildingManager.buildBuilding(accommodation);
    }
    assertEquals(25*largeBuildingScore + 2*smallBonus + largeBonus, satisfactionHandler.getPostGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getPostGameSatisfaction() +
        ", satisfaction for ten libraries, 5 stadiums, and 10 accommodations should have been 1750");
  }

  /**
   * Test 1.8.5.
   */
  @Test
  public void testProximityBonus(){
    //test no proximity bonus as long distances
    buildingManager.buildBuilding(new Stadium());
    SleepingBuilding accommodation1 = new SleepingBuilding();
    accommodation1.setLocation(new Point(101, 101));//prevent proximity bonus from having any effect
    buildingManager.buildBuilding(accommodation1);
    assertEquals(2*largeBuildingScore, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for no bonus should have been 60");
    //test small bonus correctly awarded
    SleepingBuilding accommodation2 = new SleepingBuilding();
    accommodation2.setLocation(new Point(51, 51));//inside range for small proximity bonus
    buildingManager.buildBuilding(accommodation2);
    assertEquals(3*largeBuildingScore + smallProxBonus, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for small bonus should have been 95");
    //test medium bonus correctly awarded
    SleepingBuilding accommodation3 = new SleepingBuilding();
    accommodation3.setLocation(new Point(26, 26));//inside range for medium proximity bonus
    buildingManager.buildBuilding(accommodation3);
    assertEquals(4*largeBuildingScore + smallProxBonus + mediumProxBonus, satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for medium bonus should have been 135");
    //test large bonus correctly awarded
    buildingManager.buildBuilding(new SleepingBuilding());
    assertEquals(5*largeBuildingScore + smallProxBonus + mediumProxBonus + largeProxBonus,
      satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for large bonus should have been 190");
    //check bonus is doubled for learning buildings
    buildingManager.buildBuilding(new LearningBuilding());
    assertEquals(6*largeBuildingScore + 3*smallProxBonus + 3*mediumProxBonus + 3*largeProxBonus,
      satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for learning bonuses should have been 300");
    //check bonuses don't stack for 2 of the same type of building
    buildingManager.buildBuilding(new LearningBuilding());
    assertEquals(7*largeBuildingScore + 3*smallProxBonus + 3*mediumProxBonus + 3*largeProxBonus,
      satisfactionHandler.getGameSatisfaction(),
      "satisfaction was " + satisfactionHandler.getGameSatisfaction() +
        ", satisfaction for learning bonuses should have been 330");
  }

  /**
   * Test 1.8.6.
   */
  @Test
  public void testLongBoiBonus(){
    buildingManager.buildBuilding(new LearningBuilding());
    assertEquals(30, satisfactionHandler.getGameSatisfaction(),
      "satisfaction shouldn't currently be effected by statue bonus");
    buildingManager.buildBuilding(new LongBoiStatue());
    assertEquals(45, satisfactionHandler.getGameSatisfaction(),
      "satisfaction should currently be effected by statue bonus");
  }

}
