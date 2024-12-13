package io.github.unisim.headless.buildings;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.building.Building;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest extends BuildingsTest {
  @Test
  public void testNormalAssets() {
    for (Building building : buildings) {
      Texture texture = building.getTexture();
      assertNotNull(texture, "Texture must not be null");
      assertEquals(texture, building.getTexture(), "Texture should have been cached");
    }
  }

  @Test
  public void testFireHandling() {
    for (Building building : buildings) {
      Texture texture = building.getTexture();
      building.setOnFire(true);
      assertTrue(building.isOnFire(), "Building should be on fire");
      assertNotEquals(texture, building.getTexture(), "Texture should have been changed");
    }
  }
}
