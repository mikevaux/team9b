package io.github.unisim.events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.unisim.ui.GameScreen;

/**
 * __NEW: WHOLE CLASS__ Neutral event that causes snow to fall for its duration.
 */
public class WinterHolidays extends Event {
  private float snowTimer;
  private Texture snowflake;
  private SpriteBatch spriteBatch;
  private Array<Sprite> snowflakes;

  public WinterHolidays() {
    super("Event: Winter Holidays", "Ho Ho Ho!", "events/eventWarning.png");
    snowTimer = 0;
    snowflake = new Texture("events/snow.png");
    spriteBatch = new SpriteBatch();
    snowflakes = new Array<>();
  }

  /**
   * Helper method that creates a new snowflake and adds it to the list when called.
   */
  public void createSnow() {
    float dropWidth = 0.3f;
    float dropHeight = 0.3f;
    float worldWidth = GameScreen.getViewport().getWorldWidth();
    float worldHeight = GameScreen.getViewport().getWorldHeight();

    Sprite snowdrops = new Sprite(snowflake);
    snowdrops.setSize(dropWidth, dropHeight);
    snowdrops.setX(MathUtils.random(0f, worldWidth - dropWidth));
    snowdrops.setY(worldHeight);
    snowflakes.add(snowdrops);
  }
  @Override
  public void runEvent(float delta){
    //translates all snowflakes down the screen and removes any that have left the screen
    for (int i = snowflakes.size - 1; i >= 0; i--) {
      Sprite snowdrop = snowflakes.get(i);
      float dropHeight = snowdrop.getHeight();

      snowdrop.translateY(-2f * delta);

      if (snowdrop.getY() < -dropHeight) snowflakes.removeIndex(i);
    }
    //creates a snowflake every quarter of a second
    snowTimer += delta;
    if (snowTimer > 0.25f) {
      snowTimer = 0;
      createSnow();
    }
    //draw the snowflakes to the screen
    GameScreen.getViewport().apply();
    spriteBatch.setProjectionMatrix(GameScreen.getViewport().getCamera().combined);
    spriteBatch.begin();
    for (Sprite snowdrop : snowflakes) {
      snowdrop.draw(spriteBatch);
    }
    spriteBatch.end();
  }
}
