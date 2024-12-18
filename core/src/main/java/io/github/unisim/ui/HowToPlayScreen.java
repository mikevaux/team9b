package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.GameState;
import io.github.unisim.Main;

/**
 *  __NEW: WHOLE CLASS__ The how to play screen that displays instructions and explains controls.
 */
public class HowToPlayScreen implements Screen{
  private Stage stage;
  private Table table;
  private Label introLabel;
  private Label howToPlayLabel;
  private Label outroLabel;
  private TextButton backButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();

  /**
   * Create a new HowToPlay screen and draw the initial UI layout.
   */
  public HowToPlayScreen(StartMenuScreen startScreen) {
    stage = new Stage();
    table = new Table();
    Skin skin = GameState.getInstance().getDefaultSkin();

    // Intro label
    introLabel = new Label("Welcome Boss! We have been waiting for you...", skin, "window");
    introLabel.setAlignment(Align.center);
    introLabel.setColor(new Color(0.9f, 0.9f, 0.9f, 1.0f));

    // How To Play label
    howToPlayLabel = new Label("- Press left click to select or place buildings.\n" +
      "- Hold left click and drag to pan.\n" +
      "- Use scroll wheel or pinch track pad to zoom.\n" +
      "- Place buildings to earn satisfaction and increase the size of the paychecks.\n" +
      "- Place other building types close to accommodation buildings to get a bonus.\n" +
      "- Place more accommodation and learning buildings to get a bonus.\n" +
      "- You receive a quarterly paycheck based on the number of profitable buildings placed.\n" +
      "- Watch out for events. These may impact your campus positively or negatively.\n" +
      "- Try to complete all 9 Achievements.", skin, "window");
    howToPlayLabel.setColor(new Color(0.9f, 0.9f, 0.9f, 1.0f));


    // Outro label
    outroLabel = new Label("Design the campus of your dreams and top the leaderboard\n" +
      "by reaching the highest student satisfaction possible!", skin, "window");
    outroLabel.setAlignment(Align.center);
    outroLabel.setColor(new Color(0.9f, 0.9f, 0.9f, 1.0f));

    // Back button
    backButton = new TextButton("Back", skin);
    backButton.setPosition(150, 80);
    backButton.setSize(200, 60);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        // Go back to the start menu
        Main.getInstance().setScreen(startScreen);
      }
    });

    // Add UI elements to stage
    table.setFillParent(true);
    table.center().center();
    table.pad(100, 100, 100, 100);
    table.add(introLabel).center().padBottom(15);
    table.row();
    table.add(howToPlayLabel).left().padBottom(15);
    table.row();
    table.add(outroLabel).center().padBottom(30);
    table.row();
    table.add(backButton).center().width(250).height(67);
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

    // Draw the stage containing the labels and button
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
