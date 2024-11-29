package io.github.unisim.messages;

/**
 * Provides an abstracted API for handling and displaying messages.
 */
public class MessageHandler {
  /**
   * Processes and displays a message with a title and body.
   *
   * @param title the title of the message
   * @param body the body of the message
   */
  public void showMessage(String title, String body) {
    Message message = new Message(title, body);

    System.out.println(message);
  }

  /**
   * Processes and displays a message with a title, body and icon.
   *
   * @param title the title of the message
   * @param body the body of the message
   * @param iconFilename the filename of the icon to be displayed
   */
  public void showMessage(String title, String body, String iconFilename) {
    Message message = new Message(title, body, iconFilename);

    System.out.println(message);
  }
}
