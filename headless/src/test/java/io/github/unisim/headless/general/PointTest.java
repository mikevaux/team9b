package io.github.unisim.headless.general;

import io.github.unisim.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests 1.3.x.
 */
public class PointTest extends AbstractGeneralTest {
  /**
   * Test 1.3.1.
   */
  @Test
  public void testValidValues() {
    int x = 10;
    int y = 15;

    Point point = new Point(x, y);
    assertEquals(x, point.x, "x value not set correctly.");
    assertEquals(y, point.y, "y value not set correctly.");
  }

  /**
   * Test 1.3.3.
   */
  @Test
  public void testEquals() {
    Point base = new Point(10, 10);

    assertEquals(base, base, "A Point must be equal to itself.");
    assertEquals(base, new Point(10, 10), "A Point must be equal to another point with the same x and same y values.");
    assertNotEquals(base, new Point(10, 20), "An instance of point is not equal to another instance of Point with a different x value.");
    assertNotEquals(base, new Point(20, 10), "An instance of point is not equal to another instance of Point with a different y value.");
    assertNotEquals(base, new Object(), "An instance of point is not equal to an instance of another object.");
  }

  /**
   * Test 1.3.4.
   */
  @Test
  public void testDefaultValues() {
    Point point = new Point();

    assertEquals(0, point.x, "Default x should be 0.");
    assertEquals(0, point.y, "Default y should be 0.");
  }

  /**
   * Test 1.3.5.
   */
  @Test
  public void testToString() {
    int x = 5;
    int y = 10;
    Point point = new Point(x, y);

    assertEquals("(5, 10)", point.toString(), "Converting a Point to a string does not work correctly.");
  }
}
