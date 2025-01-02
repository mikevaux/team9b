package io.github.unisim;

/**
 * __NEW: WHOLE CLASS__ An academic year within UniSim.
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
   * Cache of the string representation, to avoid recalculating many times.
   */
  private String toStringCache;

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
    invalidateToStringCache();
  }

  private void cacheToString() {
    toStringCache = String.format("%s/%s", year, year+1-2000);
  }

  private void invalidateToStringCache() {
    toStringCache = null;
  }

  @Override
  public String toString() {
    if (toStringCache == null) {
      cacheToString();
    }
    return toStringCache;
  }

  public int getYear(){
    return year;
  }
}
