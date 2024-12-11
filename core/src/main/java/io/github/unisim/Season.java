package io.github.unisim;

enum Season {
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
   * Returns the 'first' season
   *
   * @return
   */
  public static Season first() {
    return AUTUMN;
  }

  public static Season last() {
    return first().next();
  }

  private Season(String label) {
    this.label = label;
  }

  public Season next() {
    Season[] vals = values();
    return vals[(ordinal()+1) % vals.length];
  }

  @Override
  public String toString() {
    return label;
  }
}
