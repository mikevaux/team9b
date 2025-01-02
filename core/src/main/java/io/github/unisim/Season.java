package io.github.unisim;

/**
 * __NEW: WHOLE ENUM__ Represents a season of a year.
 */
public enum Season {
  AUTUMN("Autumn"),
  WINTER("Winter"),
  SPRING("Spring"),
  SUMMER("Summer");

  /**
   * The length of a Season in ms.
   */
  public static final float LENGTH = 25000;

  /**
   * The representation of this Season.
   */
  private final String label;

  /**
   * Returns the 'first' season in an Academic Year.
   *
   * @return the 'first' season in an Academic Year
   */
  public static Season first() {
    return AUTUMN;
  }

  /**
   * Returns the 'last' season in an Academic Year.
   *
   * @return the 'last' season in an Academic Year.
   */
  public static Season last() {
    return SUMMER;
  }

  /**
   * Creates a new Season, assigning the provided label
   *
   * @param label the human-readable name for this Season
   */
  Season(String label) {
    this.label = label;
  }

  /**
   * Returns the next Season, determined by entry into the enum.
   *
   * @return the next season
   */
  public Season next() {
    Season[] vals = values();
    return vals[(ordinal()+1) % vals.length];
  }

  @Override
  public String toString() {
    return label;
  }
}
