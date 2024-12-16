package io.github.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.github.unisim.ui.StartMenuScreen;

/**
 * The {@link Game} instance, constructed as a Singleton for easy access from multiple places.
 */
public class Main extends Game {
  private static Main INSTANCE;

  private Main() {
  }

  public static Main getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Main();
    }
    return INSTANCE;
  }

  @Override
  public void create() {
    setScreen(new StartMenuScreen());
  }

  @Override
  public void render() {
    super.render(); // Ensures the active screen is rendered
  }

  @Override
  public void dispose() {}

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    if (width + height == 0) {
      return;
    }
    GameState.getInstance().getFullscreenInputProcessor().resize(width, height);
  }
}
