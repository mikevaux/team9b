package io.github.unisim.headless.general;

import io.github.unisim.GameState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests 1.6.x.
 */
public class GameStateTest extends AbstractGeneralTest {

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
}
