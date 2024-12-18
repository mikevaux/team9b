package io.github.unisim.headless;

import com.badlogic.gdx.Game;

/**
 * Stub of a {@link Game} that allows us to boot a GDX runtime, without running into problems whereby
 * certain intrinsic elements of rendering within our Screens (e.g. Shaders) are not available.
 * Because we are testing distinct classes, we do not need to boot an actual instance of a Screen.
 */
public class StubMain extends Game {
  @Override
  public void create() {

  }
}
