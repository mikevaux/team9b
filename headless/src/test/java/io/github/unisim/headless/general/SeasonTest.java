package io.github.unisim.headless.general;


import io.github.unisim.Season;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static io.github.unisim.Season.*;

/**
 * Tests 1.7.x.
 */
public class SeasonTest extends AbstractGeneralTest{

  /**
   * Test 1.7.1.
   */
  @Test
  public void testNext(){
    Season season = Season.first();
    Season[] seasons= {WINTER, SPRING, SUMMER, AUTUMN};
    for (Season nextSeason: seasons) {
      assertTrue(season.next() == nextSeason);
      season = season.next();
    }
  }

}
