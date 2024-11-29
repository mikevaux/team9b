package io.github.unisim.achievements;

import io.github.unisim.messages.MessageHandler;

/**
 * Provides an abstracted API for handling UniSim Achievements.
 *
 * @see io.github.unisim.messages.MessageHandler which shows messages associated with these events
 */
public class AchievementsHandler {
  private final MessageHandler messageHandler;

  /**
   * Creates a new AchievementsHandler, with a shared MessageHandler.
   *
   * @param messageHandler the shared {@link MessageHandler} instance
   */
  public AchievementsHandler(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  /**
   * Shows the given {@link Achievement}, by interfacing with the {@link MessageHandler}.
   *
   * @param achievement the Achievement to show
   */
  public void showAchievement(Achievement achievement) {
    messageHandler.showMessage(achievement.getTitle(), achievement.getBody(), achievement.getIconFilename());
  }
}
