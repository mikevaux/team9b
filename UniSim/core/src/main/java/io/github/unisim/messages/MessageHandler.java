package io.github.unisim.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

/**
 * Provides an abstracted API for handling and displaying messages.
 */
public class MessageHandler {
  private static final int GAP_X = 32;
  private static final int GAP_Y = 24;
  private final Stage stage;
  private final ArrayList<WrappedMessage> messages;
  private int resizesToIgnore = 2;

  /**
   * Creates a new instance of a MessageHandler.
   *
   * @param stage the UI stage
   */
  public MessageHandler(Stage stage) {
    this.stage = stage;
    messages = new ArrayList<>();
  }

  /**
   * Processes and displays a message with a title and body.
   *
   * @param title the title of the message
   * @param body the body of the message
   */
  public void showMessage(String title, String body) {
    build(new Message(title, body));
  }

  /**
   * Processes and displays a message with a title, body and icon.
   *
   * @param title the title of the message
   * @param body the body of the message
   * @param iconFilename the filename of the icon to be displayed
   */
  public void showMessage(String title, String body, String iconFilename) {
    build(new Message(title, body, iconFilename));
  }

  /**
   * Triggers the laying out of the message into its 'toast' form, and sets the initial position.
   *
   * @param message the message that is being displayed
   */
  private void build(Message message) {
    Table toast = message.layout();
    stage.addActor(toast);
    messages.add(new WrappedMessage(toast));

    // Toasts slide on-screen, so initially position just off-screen
    float posX = Gdx.graphics.getWidth() + Message.WIDTH/2f;
    // messages.size()-1 returns the index of the newly added message
    float posY = getTargetY(messages.size()-1);
    toast.setPosition(posX, posY);
  }

  /**
   * Render all (active) messages
   *
   * @param delta the time in seconds since the last render
   */
  public void render(float delta) {
    decrementDurations(delta);
    moveMessages(delta);
  }

  /**
   * Decrements the remaining duration of all messages, removing them if they are done
   *
   * @param delta the time in seconds since the last render
   */
  private void decrementDurations(float delta) {
    for (int i = 0; i < messages.size(); i++) {
      WrappedMessage message = messages.get(i);
      message.decrement(delta);

      if (message.isDone()) {
        message.toast.remove();
        messages.remove(i);
        i--;
      }
    }
  }

  /**
   * Calculates positional differences and moves messages accordingly.
   * If a message is too far to the right or to low down, it shifts the x and y values slightly.
   *
   * @param delta the time in seconds since the last render
   */
  private void moveMessages(float delta) {
    float targetX = getTargetX();
    float speedX = -480 * delta;
    float speedY = 200 * delta;

    for (int i = 0; i < messages.size(); i++) {
      Table toast = messages.get(i).getToast();
      float diffX = targetX - toast.getX();
      float diffY = getTargetY(i) - toast.getY();
      float moveX = diffX < 0 ? Math.max(speedX, diffX) : 0;
      float moveY = diffY > 0 ? Math.min(speedY, diffY) : 0;
      toast.moveBy(moveX, moveY);
    }
  }

  /**
   * Calculates the target x value for all messages.
   *
   * @return the target x value
   */
  private float getTargetX() {
    return Gdx.graphics.getWidth() - Message.WIDTH/2f - GAP_X;
  }

  /**
   * Calculates the target y value for a message at the given index.
   *
   * @param idx the index of the message to calculate for
   * @return the target y value for the message at the given index
   */
  private float getTargetY(int idx) {
    int h = Gdx.graphics.getHeight();
    float y = h;
    // Pull down by half the height to position exactly at the top of the screen
    y -= Message.HEIGHT / 2f;
    // Pull down by the height of the info bar + gap
    y -= h * 0.05f + GAP_Y;
    // Pull down by the height + gap for each preceding message
    y -= idx * (Message.HEIGHT + GAP_Y);
    return y;
  }

  /**
   * Moves messages to their new positions based on the new screen size.
   */
  public void resize() {
    // Account for an awkward bug whereby resize events are fired when either a> The screen is initialized
    // and b> when the screen is first shown. This causes messages that should slide in at the
    // start to never transition.
    if (resizesToIgnore > 0) {
      resizesToIgnore --;
      return;
    }

    // Don't transition here; just move straight to the targets
    for (int i = 0; i < messages.size(); i++) {
      Table toast = messages.get(i).getToast();
      toast.setPosition(getTargetX(), getTargetY(i));
    }
  }

  /**
   * Wraps a 'toast' message up with a remaining time, which is decremented down to zero.
   */
  private static class WrappedMessage {
    private final Table toast;
    private float remaining = 5;

    WrappedMessage(Table toast) {
      this.toast = toast;
    }

    public Table getToast() {
      return toast;
    }

    public void decrement(float d) {
      this.remaining -= d;
    }

    public boolean isDone() {
      return remaining <= 0;
    }
  }
}
