package io.github.unisim.headless.general;

import io.github.unisim.BuildingManager;
import io.github.unisim.building.BasketballCourt;
import io.github.unisim.events.WinterHolidays;
import io.github.unisim.satisfaction.SatisfactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SatisfactionHandlerTest extends AbstractGeneralTest {
  private BuildingManager buildingManager;
  private SatisfactionHandler satisfactionHandler;

  @BeforeEach
  public void boot() {
    buildingManager = new BuildingManager(null);
    satisfactionHandler = new SatisfactionHandler(buildingManager, new WinterHolidays());
  }

  @Test
  public void testX() {
    assertEquals(0, satisfactionHandler.getSatisfaction(), "Initial satisfaction should be zero.");
  }

  @Test
  public void testY() {
    buildingManager.buildBuilding(new BasketballCourt());
    assertEquals(15, satisfactionHandler.getSatisfaction(), "Should have been 15");
  }
}
