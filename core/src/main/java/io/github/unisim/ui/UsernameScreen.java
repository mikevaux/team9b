package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;

/**
 * __NEW: WHOLE CLASS__ username screen that allows a user to enter
 * a username to be displayed on the leaderboard.
 */
public class UsernameScreen implements Screen {
  private Stage stage;
  private Table table;
  private TextField usernameField;
  private TextButton leaderboardButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private String username;

  /**
   * Create a new UsernameScreen and draw the initial UI layout.
   */
  public UsernameScreen() {
    stage = new Stage();
    table = new Table();
    Skin skin = GameState.getInstance().getDefaultSkin();

    // Username text field
    usernameField = new TextField("Enter Username", skin);

    // Leaderboard button
    leaderboardButton = new TextButton("Enter", skin);
    leaderboardButton.setPosition(150, 80);
    leaderboardButton.setSize(200, 60);
    leaderboardButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        username = usernameField.getText();
        // will lead to leaderboard screen in future
      }
    });

    // Add UI elements to stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(usernameField).center().width(250).height(67).padBottom(10);
    table.row();
    table.add(leaderboardButton).center();
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

    // Draw the stage containing the volume slider and buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }

}
