package io.github.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.unisim.ui.GameScreen;
import io.github.unisim.ui.SettingsScreen;
import io.github.unisim.ui.StartMenuScreen;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains a collection of settings and references that should be available globally.
 */
public class GameState {
  public static InputProcessor fullscreenInputProcessor = new FullscreenInputProcessor();
  public static Screen gameScreen = new GameScreen();
  public static Screen startScreen = new StartMenuScreen();
  public static Screen settingScreen = new SettingsScreen();
  public static Screen currentScreen;
  // Create an unmodifiable set containing the IDs of all buildable tiles
  // we use a set to make searching more efficient
  public static Set<Integer> buildableTiles = Stream.of(
      14, 15).collect(Collectors.toUnmodifiableSet()
  );
  public static boolean paused = true;
  public static boolean gameOver = false;

  ////////////////////////
  // Below is the beginning of a move toward a Singleton pattern for GameState. The current setup is causing problems.
  // One example of this was a race condition, whereby a static member of GameState was unavailable at game boot time.
  ////////////////////////

  private static GameState INSTANCE;
  private static final String currency = "£";
  private static final String colourPrimaryHex = "A84D9D";
  private static final String colourSecondaryHex = "414C4E";
  private int quarterlyIncome = 20_000;
  private final Skin defaultSkin;
  private final Settings settings;

  public static GameState getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new GameState();
    }
    return INSTANCE;
  }

  private GameState() {
    settings = new Settings();
    defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
  }

  public String getCurrency() {
    return currency;
  }

  public Color getColourPrimary() {
    return Color.valueOf(colourPrimaryHex);
  }

  public Color getColourSecondary() {
    return Color.valueOf(colourSecondaryHex);
  }

  public int getQuarterlyIncome() {
    return quarterlyIncome;
  }

  public Skin getDefaultSkin() {
    return defaultSkin;
  }

  public Settings getSettings() {
    return settings;
  }

  public void increaseQuarterlyIncome(int increase) {
    quarterlyIncome += increase;
  }
}
