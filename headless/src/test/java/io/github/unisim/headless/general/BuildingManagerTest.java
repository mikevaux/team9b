package io.github.unisim.headless.general;

import io.github.unisim.BuildingManager;
import org.junit.jupiter.api.BeforeEach;

public class BuildingManagerTest extends AbstractGeneralTest {
  private BuildingManager buildingManager;

  @BeforeEach
  public void boot() {
    buildingManager = new BuildingManager(null);
  }
}
