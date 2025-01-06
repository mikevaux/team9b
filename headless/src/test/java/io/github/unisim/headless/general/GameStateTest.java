package io.github.unisim.headless.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.unisim.Bank;
import io.github.unisim.FullscreenInputProcessor;
import io.github.unisim.GameState;
import io.github.unisim.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests 1.6.x.
 */
public class GameStateTest extends AbstractGeneralTest {

  @BeforeEach
  public void boot() {
    GameState.wipeInstance();
  }

  /**
   * Test 1.6.1.
   */
  @Test
  public void testGetInstance(){
    assertTrue(GameState.getInstance() instanceof GameState, "getInstance should provide an instance of GameState");
  }

  /**
   * Test 1.6.2.
   */
  @Test
  public void testWipeInstance(){
    GameState instance = GameState.getInstance();
    assertFalse(instance == null, "instance should not be null initially");
    instance.togglePaused();
    assertFalse(instance.isPaused(), "instance should have had paused set to false");
    GameState.wipeInstance();
    instance = GameState.getInstance();
    assertTrue(instance.isPaused(), "wipe instance should have reset paused to true");
  }

  /**
   * Test1.6.3.
   */
  @Test
  public void testCurrency(){
    GameState gameState = GameState.getInstance();
    assertEquals("£", gameState.getCurrency(), "currency getter not behaving as expected");
  }

  /**
   * Test1.6.4.
   */
  @Test
  public void testColorPrimary(){
    GameState gameState = GameState.getInstance();
    assertEquals(Color.valueOf("A84D9D"), gameState.getColourPrimary(), "primary colour getter not behaving as expected");
  }

  /**
   * Test1.6.5.
   */
  @Test
  public void testColorSecondary(){
    GameState gameState = GameState.getInstance();
    assertEquals(Color.valueOf("414C4E"), gameState.getColourSecondary(), "secondary colour getter not behaving as expected");
  }

  /**
   * Test1.6.6.
   */
  @Test
  public void testQuarterlyIncome(){
    int initialIncome = 50_000;
    int increase = 10_000;
    GameState gameState = GameState.getInstance();
    // test getter
    assertEquals(initialIncome, gameState.getQuarterlyIncome(), "quarterly income getter not behaving as expected");
    // test increment
    gameState.increaseQuarterlyIncome(increase);
    assertEquals(initialIncome + increase, gameState.getQuarterlyIncome(), "increaseQuarterlyIncome not behaving as expected");
  }

  /**
   * Test1.6.7.
   */
  @Test
  public void testFullScreenInputProcessor(){
    GameState gameState = GameState.getInstance();
    assertTrue(gameState.getFullscreenInputProcessor() instanceof FullscreenInputProcessor, "Input Processor getter not behaving as expected");
  }

  /**
   * Test1.6.8.
   */
  @Test
  public void testSkin(){
    GameState gameState = GameState.getInstance();
    assertTrue(gameState.getDefaultSkin() instanceof Skin, "skin getter not behaving as expected");
  }

  /**
   * Test1.6.9.
   */
  @Test
  public void testSettings(){
    GameState gameState = GameState.getInstance();
    assertTrue(gameState.getSettings() instanceof Settings, "settings getter not behaving as expected");
  }

  /**
   * Test1.6.10.
   */
  @Test
  public void testPaused(){
    GameState gameState = GameState.getInstance();
    //test getter
    assertTrue(gameState.isPaused(), "paused getter not behaving as expected");
    //test setter
    gameState.setPaused(false);
    assertFalse(gameState.isPaused(),"paused setter not behaving as expected");
    //test toggle
    gameState.togglePaused();
    assertTrue(gameState.isPaused(),"toggle paused not working as expected");
    gameState.togglePaused();
    assertFalse(gameState.isPaused(),"toggle paused not working as expected");
  }

  /**
   * Test1.6.11.
   */
  @Test
  public void testGameOver(){
    GameState gameState = GameState.getInstance();
    //test getter
    assertFalse(gameState.isGameOver(), "game over getter not behaving as expected");
    //test setter
    gameState.setGameOver(true);
    assertTrue(gameState.isGameOver(),"game over setter not behaving as expected");
  }
}
