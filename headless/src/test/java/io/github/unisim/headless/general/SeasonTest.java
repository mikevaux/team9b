package io.github.unisim.headless.general;


import io.github.unisim.Season;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static io.github.unisim.Season.*;

/**
 * Tests 1.7.x.
 */
public class SeasonTest extends AbstractGeneralTest{
  private Season[] seasons  = {WINTER, SPRING, SUMMER, AUTUMN};
  private String[] seasonStrings = {"Summer", "Autumn", "Winter", "Spring"};

  /**
   * Test 1.7.1.
   */
  @Test
  public void testNext(){
    Season season = Season.first();
    for (Season nextSeason: seasons) {
      assertTrue(season.next() == nextSeason,"season.next() not working as expected");
      season = season.next();
    }
  }

  /**
   * Test 1.7.2.
   */
  @Test
  public void testToString(){
    Season season = Season.last();
    for (int i = 0; i < 4; i++) {
      assertTrue(season.toString() == seasonStrings[i],"toString() not working as expected");
      season = season.next();
    }
  }
}
