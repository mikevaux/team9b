package io.github.unisim.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;
import io.github.unisim.Main;
import io.github.unisim.achievements.PressStartToPlay;

/**
 * The start menu screen which presents the player with the option to start the
 * game
 * or access the settings menu.
 */
public class StartMenuScreen implements Screen {
  private Stage stage;
  private Table table;
  private TextButton playButton;
  private TextButton settingsButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  /**
   * Create a new StartMenuScreen and draw the initial UI layout.
   */
  public StartMenuScreen() {
    stage = new Stage();
    table = new Table();
    Skin skin = GameState.getInstance().getDefaultSkin();
    // Reference to this screen for use inside a different context
    StartMenuScreen _this = this;

    // Play button
    playButton = new TextButton("Start", skin);
    playButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the game screen
        Main.getInstance().setScreen(new GameScreen());
        PressStartToPlay.setDisplay(true); //display the PressPlayToStart achievement
      }
    });

    // Settings button
    settingsButton = new TextButton("Settings", skin);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Switch to the settings screen
        Main.getInstance().setScreen(new SettingsScreen(_this));
      }
    });

    // Add UI elements to the stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(playButton).center().width(250).height(100).padBottom(10);
    table.row();
    table.add(settingsButton).center().width(250).height(67);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.getInstance().getFullscreenInputProcessor());
    inputMultiplexer.addProcessor(stage);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.getInstance().getColourSecondary());

    // Draw the stage containing buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {}

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    stage.dispose();
  }
}
