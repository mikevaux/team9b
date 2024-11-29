package io.github.unisim.messages;

/**
 * A message to be displayed in the game screen.
 */
class Message {
  private final String title;
  private final String body;
  private String iconFilename;

  /**
   * Creates a new message with just a title and body.
   *
   * @param title the title of the message
   * @param body the body of the message
   */
  Message(String title, String body) {
    this.title = title;
    this.body = body;
  }

  /**
   * Creates a new message with a title, body and icon.
   *
   * @param title the title of the message
   * @param body the body of the message
   * @param iconFilename the filename of the icon
   */
  Message(String title, String body, String iconFilename) {
    this.title = title;
    this.body = body;
    this.iconFilename = iconFilename;
  }

  @Override
  public String toString() {
    return title + "\n" + body + "\n" + iconFilename;
  }
}
