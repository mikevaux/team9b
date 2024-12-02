package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.unisim.Bank;
import io.github.unisim.GameState;
import io.github.unisim.Timer;
import io.github.unisim.achievements.AchievementsHandler;
import io.github.unisim.events.EventsHandler;
import io.github.unisim.events.Fire;
import io.github.unisim.events.LongboiDay;
import io.github.unisim.events.WinterHolidays;
import io.github.unisim.messages.MessageHandler;
import io.github.unisim.world.UiInputProcessor;
import io.github.unisim.world.World;
import io.github.unisim.world.WorldInputProcessor;

import java.util.ArrayList;
import java.util.Random;


/**
 * Game screen where the main game is rendered and controlled.
 * Supports pausing the game with a pause menu.
 */
public class GameScreen implements Screen {
  private ArrayList<String> randomEvents;
  private boolean event1 = true;
  private boolean event2 = true;
  private boolean event3 = true;
  private Texture snowflake;
  private SpriteBatch spriteBatch;
  private FitViewport viewport;
  private Array<Sprite> snowflakes;
  private float snowTimer;
  private float winterHolidaysTimer;
  public static boolean winterHolidays = false;

  private World world = new World();
  private Stage stage = new Stage(new ScreenViewport());
  private InfoBar infoBar;
  private BuildingMenu buildingMenu;
  private Timer timer;
  private final Bank bank;
  private final MessageHandler messageHandler;
  private final EventsHandler eventsHandler;
  private final AchievementsHandler achievementsHandler;
  private InputProcessor uiInputProcessor = new UiInputProcessor(stage);
  private InputProcessor worldInputProcessor = new WorldInputProcessor(world);
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private GameOverMenu gameOverMenu = new GameOverMenu();

  /**
   * Constructor for the GameScreen.
   */
  public GameScreen() {
    timer = new Timer(300_000);
    randomiseEvent();
    infoBar = new InfoBar(stage, timer, world);
    buildingMenu = new BuildingMenu(stage, world);
    bank = new Bank();
    messageHandler = new MessageHandler();
    eventsHandler = new EventsHandler(messageHandler);
    achievementsHandler = new AchievementsHandler(messageHandler);

    inputMultiplexer.addProcessor(GameState.fullscreenInputProcessor);
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(uiInputProcessor);
    inputMultiplexer.addProcessor(worldInputProcessor);

    snowflake = new Texture("events/snow.png");
    spriteBatch = new SpriteBatch();
    viewport = new FitViewport(8, 5);
    snowflakes = new Array<>();
  }



  private void createSnow() {
    float dropWidth = 0.3f;
    float dropHeight = 0.3f;
    float worldWidth = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();

    Sprite snowdrops = new Sprite(snowflake);
    snowdrops.setSize(dropWidth, dropHeight);
    snowdrops.setX(MathUtils.random(0f, worldWidth - dropWidth));
    snowdrops.setY(worldHeight);
    snowflakes.add(snowdrops);
  }

  @Override
  public void show() {
  }

  private void checkEvents(float delta){
    if (timer.getRemainingTime() < 290_000 && event1){
      event1 = false;
      event(0);
    }else if (timer.getRemainingTime() < 280_000 && event2){
      event2 = false;
      event(1);
    }else if (timer.getRemainingTime() < 270_000 && event3){
      event3 = false;
      event(2);
    }
    if (winterHolidays){
      winterHolidaysTimer += delta;
      if (winterHolidaysTimer > 20f) {
        winterHolidays = false;
        createSnow();
      }
    }
  }
  private void renderSnow(float delta){
    for (int i = snowflakes.size - 1; i >= 0; i--) {
      Sprite snowdrop = snowflakes.get(i);
      float dropHeight = snowdrop.getHeight();

      snowdrop.translateY(-2f * delta);

      if (snowdrop.getY() < -dropHeight) snowflakes.removeIndex(i);
    }
    snowTimer += delta;
    if (snowTimer > 0.25f) {
      snowTimer = 0;
      createSnow();
    }

    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    spriteBatch.begin();
    for (Sprite snowdrop : snowflakes) {
      snowdrop.draw(spriteBatch);
    }
    spriteBatch.end();
  }

  @Override
  public void render(float delta) {
    world.render();
    float dt = Gdx.graphics.getDeltaTime();
    if (!GameState.paused && !GameState.gameOver) {
      if (!timer.tick(dt * 1000)) {
        GameState.gameOver = true;
        Gdx.input.setInputProcessor(gameOverMenu.getInputProcessor());
      }
    }
    stage.act(dt);
    infoBar.update();
    buildingMenu.update();
    stage.draw();
    if (GameState.gameOver) {
      world.zoom((world.getMaxZoom() - world.getZoom()) * 2f);
      world.pan((150 - world.getCameraPos().x) / 10, -world.getCameraPos().y / 10);
      gameOverMenu.render(delta);
    }

    checkEvents(delta);
    if (winterHolidays){
      renderSnow(delta);
    }
  }


  private void randomiseEvent(){
    randomEvents= new ArrayList<String>();
    ArrayList<String> events= new ArrayList<String>();
    events.add("fire");
    events.add("duck");
    events.add("winter");
    Random ran = new Random();
    int x = ran.nextInt(3);
    randomEvents.add(events.get(x));
    events.remove(x);
    x = ran.nextInt(2);
    randomEvents.add(events.get(x));
    events.remove(x);
    randomEvents.add(events.get(0));
  }

  private void event(int eventNumber){
    String selectedEvent = randomEvents.get(eventNumber);
    switch (selectedEvent) {
      case "fire":
        Fire fireEvent = new Fire();
        eventsHandler.showEvent(fireEvent);
        break;
      case "winter":
        WinterHolidays winterEvent = new WinterHolidays();
        eventsHandler.showEvent(winterEvent);
        break;
      case "duck":
        LongboiDay duckEvent = new LongboiDay();
        eventsHandler.showEvent(duckEvent);
    }
  }





  @Override
  public void resize(int width, int height) {
    world.resize(width, height);
    stage.getViewport().update(width, height, true);
    infoBar.resize(width, height);
    buildingMenu.resize(width, height);
    gameOverMenu.resize(width, height);
    viewport.update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(inputMultiplexer);

    if (GameState.gameOver) {
      GameState.gameOver = false;
      GameState.paused = true;
      timer.reset();
      world.reset();
      infoBar.reset();
      buildingMenu.reset();
    }
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
