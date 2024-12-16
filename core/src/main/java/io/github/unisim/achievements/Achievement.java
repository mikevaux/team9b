package io.github.unisim.achievements;

/**
 * __NEW: WHOLE CLASS__ Provides attributes and base methods for all UniSim {@link Achievement}s
 */
abstract public class Achievement {
  private final String title;
  private final String body;
  private final String iconFilename;

  /**
   * Creates a new {@link Achievement} (which always have a title, body and icon).
   *
   * @param title the title of the Achievement
   * @param body the body of the Achievement
   * @param iconFilename the icon for the Achievement
   */
  public Achievement(String title, String body, String iconFilename) {
    this.title = title;
    this.body = body;
    this.iconFilename = iconFilename;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public String getIconFilename() {
    return iconFilename;
  }
}
