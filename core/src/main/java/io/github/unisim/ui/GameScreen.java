package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.unisim.Bank;
import io.github.unisim.GameState;
import io.github.unisim.Timer;
import io.github.unisim.achievements.AchievementsHandler;
import io.github.unisim.events.EventsHandler;
import io.github.unisim.messages.MessageHandler;
import io.github.unisim.satisfaction.SatisfactionHandler;
import io.github.unisim.world.UiInputProcessor;
import io.github.unisim.world.World;
import io.github.unisim.world.WorldInputProcessor;


/**
 * Game screen where the main game is rendered and controlled.
 * Supports pausing the game with a pause menu.
 */
public class GameScreen implements Screen {
  private World world = new World();
  private Stage stage = new Stage(new ScreenViewport());
  private InfoBar infoBar;
  private BuildingMenu buildingMenu;
  private Timer timer;
  private final MessageHandler messageHandler;
  private final EventsHandler eventsHandler;
  private final AchievementsHandler achievementsHandler;
  private final SatisfactionHandler satisfactionHandler;
  private InputProcessor uiInputProcessor = new UiInputProcessor(stage);
  private InputProcessor worldInputProcessor = new WorldInputProcessor(world);
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private GameOverMenu gameOverMenu;
  private static FitViewport viewport;
  private static final float gameDuration = 300_000f;

  /**
   * Constructor for the GameScreen.
   */
  public GameScreen() {
    messageHandler = new MessageHandler(stage);
    timer = new Timer(gameDuration);
    infoBar = new InfoBar(stage, timer, world);
    buildingMenu = new BuildingMenu(stage, world, messageHandler);
    eventsHandler = new EventsHandler(messageHandler, world.getBuildingManager(), buildingMenu);
    satisfactionHandler = new SatisfactionHandler(infoBar, world.getBuildingManager(), eventsHandler);
    achievementsHandler = new AchievementsHandler(messageHandler);
    inputMultiplexer.addProcessor(GameState.getInstance().getFullscreenInputProcessor());
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(uiInputProcessor);
    inputMultiplexer.addProcessor(worldInputProcessor);
    viewport = new FitViewport(8, 5);
    gameOverMenu = new GameOverMenu(satisfactionHandler);
  }

  public static float getGameDuration() {
    return gameDuration;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }


  @Override
  public void render(float delta) {
    world.render();
    float dt = Gdx.graphics.getDeltaTime();
    if (!GameState.getInstance().isPaused() && !GameState.getInstance().isGameOver()) {
      if (!timer.tick(dt * 1000)) {
        GameState.getInstance().setGameOver(true);
        Gdx.input.setInputProcessor(gameOverMenu.getInputProcessor());
      }
    }
    stage.act(dt);
    infoBar.update();
    buildingMenu.update();
    messageHandler.render(delta);
    stage.draw();
    if (GameState.getInstance().isGameOver()) {
      world.zoom((world.getMaxZoom() - world.getZoom()) * 2f);
      world.pan((150 - world.getCameraPos().x) / 10, -world.getCameraPos().y / 10);
      gameOverMenu.render(delta);
    }

    eventsHandler.checkEvents(delta, timer);
    eventsHandler.runCurrentEvent(delta);
    achievementsHandler.displayAchievements(delta);
    satisfactionHandler.updateSatisfaction();
  }

  @Override
  public void resize(int width, int height) {
    world.resize(width, height);
    stage.getViewport().update(width, height, true);
    infoBar.resize(width, height);
    buildingMenu.resize(width, height);
    gameOverMenu.resize(width, height);
    viewport.update(width, height, true);
    messageHandler.resize();
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {}

  public static FitViewport getViewport(){
    return viewport;
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    world.dispose();
    stage.dispose();
  }
}
