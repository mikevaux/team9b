package io.github.unisim.headless.buildings;

import io.github.unisim.building.*;
import io.github.unisim.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public abstract class AbstractBuildingTest extends AbstractHeadlessGdxTest {
  ArrayList<Building> buildings;

  @BeforeEach
  public void boot() {
    buildings = new ArrayList<>();
    buildings.add(new EatingBuilding());
    buildings.add(new LearningBuilding());
    buildings.add(new BasketballCourt());
    buildings.add(new Stadium());
    buildings.add(new SleepingBuilding());
  }
}
