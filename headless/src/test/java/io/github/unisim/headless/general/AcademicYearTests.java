package io.github.unisim.headless.general;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.github.unisim.AcademicYear;

import java.time.Year;

/**
 * Tests 1.4.x.
 */
public class AcademicYearTests extends AbstractGeneralTest {
  private final int initial = 1;

  /**
   * Test 1.4.1.
   */
  @Test
  public void testIncrement(){
    AcademicYear year1 = new AcademicYear(initial);
    year1.increment();
    assertEquals(year1.getYear(), initial + 1, "increment should increase the value by 1");
  }

  /**
   * Test 1.4.2.
   */
  @Test
  public void testToString(){
    AcademicYear year1 = new AcademicYear(initial);
    assertEquals(year1.toString(), String.valueOf(initial) + "/" + (initial-1999), "year is not converted to string correctly");
  }
}


