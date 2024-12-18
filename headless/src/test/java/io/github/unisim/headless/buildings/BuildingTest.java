package io.github.unisim.headless.buildings;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.GameState;
import io.github.unisim.Point;
import io.github.unisim.building.Building;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
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
      building.flip();
      assertFalse(building.isFlipped(), "Building should have been un-flipped.");
      assertEquals(sizeX, building.getSize().x, "Flipping back should have reset the size's x and y.");
      assertEquals(sizeY, building.getSize().y, "Flipping back should have reset the size's x and y.");

    }
  }

  /**
   * Test 2.1.4.
   */
  @Test
  public void testTypes() {
    for (Building building : buildings) {
      assertNotNull(building.getType(), "Type must not be null.");
    }
  }

  /**
   * Test 2.1.5.
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

  /**
   * Test 2.1.6.
   */
  @Test
  public void testCost() {
    for (Building building : buildings) {
      assertTrue(building.getCost() > 0);

      // This separately recreates the handling for formatting that should be used by a `Building`.
      NumberFormat f = NumberFormat.getInstance();
      f.setGroupingUsed(true);
      String currency = GameState.getInstance().getCurrency();

      assertEquals(
        String.format("%s%s", currency, f.format(building.getCost())),
        building.displayCost(),
        "Cost is not displayed correctly."
      );
    }
  }

  /**
   * Test 2.1.7.
   */
  @Test
  public void testIncomeGeneration() {
    for (Building building : buildings) {
      assertTrue(building.getIncomeGeneration() >= 0);
    }
  }

  /**
   * Test 2.1.8.
   */
  @Test
  public void testTextureScale() {
    for (Building building : buildings) {
      assertTrue(building.getTextureScale() > 0);
    }
  }

  /**
   * Test 2.1.9.
   */
  @Test
  public void testTextureOffset() {
    for (Building building : buildings) {
      assertNotNull(building.getTextureOffset());
    }
  }

  /**
   * Test 2.1.10.
   */
  @Test
  public void testLocation() {
    for (Building building : buildings) {
      // Location defaults to `new Point(0, 0)`
      assertNotNull(building.getLocation());

      Point location = new Point(1, 1);
      building.setLocation(location);
      assertEquals(location.x, building.getLocation().x, "Location x value not set correctly.");
      assertEquals(location.y, building.getLocation().y, "Location y value not set correctly.");
    }
  }
}
