package io.github.unisim;

/**
 * An academic year within UniSim.
 */
public class AcademicYear {
  /**
   * The first year for UniSim.
   */
  public static final int FIRST_YEAR = 2024;

  /**
   * The current in-game year
   */
  private int year;

  public AcademicYear(int year) {
    this.year = year;
  }

  public void increment() {
    year ++;
  }

  @Override
  public String toString() {
    return String.format("%s/%s", year, year+1-2000);
  }
}
