package io.github.unisim.ui;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.unisim.Bank;
import io.github.unisim.GameState;
import io.github.unisim.Main;
import io.github.unisim.satisfaction.SatisfactionHandler;

/**
 * Menu that is displayed when the timer has run out. This is where the final score
 * will be calculated in future.
 */
public class GameOverMenu {
  private Stage stage;
  private final ShapeActor bar = new ShapeActor(GameState.getInstance().getColourSecondary());
  private Table table;
  private TextButton mainMenuButton;
  private TextButton leaderboardButton;
  private Cell<TextButton> buttonCell1;
  private Cell<TextButton> buttonCell2;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private SatisfactionHandler satisfactionHandler;

  /**
   * Creates a new GameOverMenu and initialises all events and UI elements used in the menu.
   */
  public GameOverMenu(SatisfactionHandler handler) {
    stage = new Stage(new ScreenViewport());
    table = new Table();
    Skin skin = GameState.getInstance().getDefaultSkin();
    this.satisfactionHandler = handler;

    // Play button
    mainMenuButton = new TextButton("Return to Main Menu", skin);
    mainMenuButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Go back to a fresh start screen
        Main.getInstance().setScreen(new StartMenuScreen());
        // Wipe all singletons to reset the game state
        GameState.wipeInstance();
        Bank.wipeInstance();
      }
    });

    // Leaderboard button
    leaderboardButton = new TextButton("View Leaderboard", skin);
    leaderboardButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        satisfactionHandler.updatePostGameSatisfaction();
        // go to username screen
        Main.getInstance().setScreen(new UsernameScreen(satisfactionHandler.getPostGameSatisfaction(), false));
      }
    });

    // Add UI elements to the stage
    buttonCell1 = table.add(mainMenuButton).center();
    buttonCell2 = table.add(leaderboardButton).center();
    stage.addActor(bar);
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.getInstance().getFullscreenInputProcessor());
    inputMultiplexer.addProcessor(stage);
  }


  public void render(float delta) {
    stage.act(delta);
    stage.draw();
  }

  /**
   * Called when the game window is resized and we need to adjust the scale of the UI elements.

   * @param width - The new game window width in pixels
   * @param height - The new game window height in pixels
   */
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
    table.setBounds(0, 0, width, height * 0.1f);
    bar.setBounds(0, 0, width, height * 0.1f);
    buttonCell1.width(width * 0.3f).height(height * 0.1f);
    buttonCell2.width(width * 0.3f).height(height * 0.1f);
  }

  public InputProcessor getInputProcessor() {
    return inputMultiplexer;
  }
}
