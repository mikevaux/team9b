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

  /**
   * Creates a new {@link AcademicYear} for the given year.
   *
   * @param year the year, e.g. 2024
   */
  public AcademicYear(int year) {
    this.year = year;
  }

  /**
   * Increments the year by 1.
   */
  public void increment() {
    year ++;
  }

  @Override
  public String toString() {
    return String.format("%s/%s", year, year+1-2000);
  }
}
