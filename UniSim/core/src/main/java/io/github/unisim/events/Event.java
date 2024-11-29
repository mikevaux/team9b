package io.github.unisim.events;

/**
 * Provides attributes and base methods for all UniSim {@link Event}s
 */
abstract public class Event {
  private final String title;
  private final String body;
  private String iconFilename;

  /**
   * Creates a new {@link Event} with just a title and body.
   *
   * @param title the title of the Event
   * @param body the body of the Event
   */
  public Event(String title, String body) {
    this.title = title;
    this.body = body;
  }
  /**
   * Creates a new {@link Event} with a title, body and icon.
   *
   * @param title the title of the Event
   * @param body the body of the Event
   * @param iconFilename the icon for the Event
   */
  public Event(String title, String body, String iconFilename) {
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
