package io.github.unisim.events;

import io.github.unisim.messages.MessageHandler;

/**
 * Provides an abstracted API for handling UniSim Events.
 *
 * @see io.github.unisim.messages.MessageHandler which shows messages associated with these events
 */
public class EventsHandler {
  private final MessageHandler messageHandler;

  /**
   * Creates a new EventsHandler, with a shared MessageHandler.
   *
   * @param messageHandler the shared {@link MessageHandler} instance
   */
  public EventsHandler(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  /**
   * Shows the given {@link Event}, by interfacing with the {@link MessageHandler}.
   *
   * @param event the Event to show
   */
  public void showEvent(Event event) {
    messageHandler.showMessage(event.getTitle(), event.getBody(), event.getIconFilename());
  }
}
