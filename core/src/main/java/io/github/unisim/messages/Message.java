package io.github.unisim.messages;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import io.github.unisim.GameState;

/**
 * A message to be displayed in the game screen.
 */
class Message {
  static final int WIDTH = 320;
  static final int HEIGHT = 84;
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

  /**
   * Constructs and returns a Scene2d.UI Table representation of a message, using the "toast" format.
   *
   * @return the constructed table
   */
  Table layout() {
    Skin skin = GameState.getInstance().getDefaultSkin();
    boolean hasIcon = iconFilename != null;

    // Scene2d.ui.Table doesn't support rowspan, so we need to use a nested table instead
    Table text = new Table();
    text.row().padBottom(8);
    text.add(new Label(title, skin, "window")).left();
    text.row();
    text.add(new Label(body, skin)).left();

    Table toast = new Table().left();
    toast.setBackground(makeBackground());
    toast.row().pad(20);
    if (hasIcon) {
      Image image = new Image(new Texture(iconFilename));
      image.setScaling(Scaling.fit);
      toast.add(image).size(42).padRight(8);
    }
    toast.add(text);

    // For some reason, setting the background colour on an absolutely positioned Table
    // did not work, but it does work on nested tables. This therefore just wraps the
    // actual useful table in another table.
    Table toastContainer = new Table();
    toastContainer.add(toast).width(WIDTH).minHeight(HEIGHT);
    return toastContainer;
  }

  /**
   * Constructs and returns a {@link TextureRegionDrawable} with a background colour from our palette.
   *
   * @return the Drawable
   */
  private TextureRegionDrawable makeBackground() {
    Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
    bgPixmap.setColor(GameState.getInstance().getColourSecondary());
    bgPixmap.fill();
    return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
  }
}
