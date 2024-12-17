package io.github.unisim.headless.buildings;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest extends AbstractBuildingTest {
  /**
   * Test 2.1.1.
   */
  @Test
  public void testNormalAssets() {
    for (Building building : buildings) {
      Texture texture = building.getTexture();
      assertNotNull(texture, "Texture must not be null");
      assertEquals(texture, building.getTexture(), "Texture should have been cached");
    }
  }

  /**
   * Test 2.1.2.
   */
  @Test
  public void testFireHandling() {
    for (Building building : buildings) {
      Texture texture = building.getTexture();
      building.setOnFire(true);
      assertTrue(building.isOnFire(), "Building should be on fire");
      assertNotEquals(texture, building.getTexture(), "Texture should have been changed");
    }
  }

  /**
   * Test 2.1.3.
   */
  @Test
  public void testFlipping() {
    for (Building building : buildings) {
      int sizeX = building.getSize().x;
      int sizeY = building.getSize().y;

      assertFalse(building.isFlipped(), "Building should not be flipped initially.");
      building.flip();
      assertTrue(building.isFlipped(), "Building should have been flipped.");
      assertEquals(sizeY, building.getSize().x, "Flipping should invert the size's x and y.");
      assertEquals(sizeX, building.getSize().y, "Flipping should invert the size's x and y.");
    }
  }

  /**
   * Test 2.1.4
   */
  @Test
  public void testTypes() {
    for (Building building : buildings) {
      assertInstanceOf(BuildingType.class, building.getType(), "Type should be an instance of BuildingType.");
    }
  }

  /**
   * Test 2.1.5
   */
  @Test
  public void testNames() {
    Set<String> namesRead = new HashSet<>();

    for (Building building : buildings) {
      String name = building.getName();
      assertFalse(name.isEmpty(), "A Building's name must not be empty.");
      assertFalse(namesRead.contains(name), "Each building must have a unique name.");
      namesRead.add(name);
    }
  }
}
