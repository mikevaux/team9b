package io.github.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.unisim.ui.GameScreen;
import io.github.unisim.ui.SettingsScreen;
import io.github.unisim.ui.StartMenuScreen;

/**
 * Contains a collection of settings and references that should be available globally.
 */
public class GameState {
  public static Screen gameScreen = new GameScreen();
  public static Screen startScreen = new StartMenuScreen();
  public static Screen settingScreen = new SettingsScreen();
  public static Screen currentScreen;

  ////////////////////////
  // Below is the beginning of a move toward a Singleton pattern for GameState. The current setup is causing problems.
  // One example of this was a race condition, whereby a static member of GameState was unavailable at game boot time.
  ////////////////////////

  private static GameState INSTANCE;
  private static final String currency = "£";
  private static final String colourPrimaryHex = "A84D9D";
  private static final String colourSecondaryHex = "414C4E";
  private int quarterlyIncome = 20_000;
  private final FullscreenInputProcessor fullscreenInputProcessor;
  private final Skin defaultSkin;
  private final Settings settings;
  private boolean paused = true;
  private boolean gameOver = false;

  public static GameState getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new GameState();
    }
    return INSTANCE;
  }

  private GameState() {
    fullscreenInputProcessor = new FullscreenInputProcessor();
    defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    settings = new Settings();
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

  public FullscreenInputProcessor getFullscreenInputProcessor() {
    return fullscreenInputProcessor;
  }

  public Skin getDefaultSkin() {
    return defaultSkin;
  }

  public Settings getSettings() {
    return settings;
  }

  public boolean isPaused() {
    return paused;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void increaseQuarterlyIncome(int increase) {
    quarterlyIncome += increase;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public void togglePaused() {
    paused = !paused;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }
}
