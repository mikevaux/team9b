package io.github.unisim.headless.general;

import io.github.unisim.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimerTest extends AbstractGeneralTest {
  static Timer timer;
  static float initialTime = 10_000;

  @BeforeEach
  public void boot() {
    timer = new Timer(initialTime);
  }

  /**
   * Test 1.1.1.
   */
  @Test
  public void testInitialTime(){
    assertEquals(initialTime, timer.getRemainingTime(), STANDARD_DELTA, "time is not set as expected");
  }

  /**
   * Test 1.1.2.
   */
  @Test
  public void testTimerDecrements(){
    float tickSize1 = 1_000;
    float newTime1 = initialTime - tickSize1;
    timer.tick(tickSize1);
    assertEquals(newTime1, timer.getRemainingTime(), STANDARD_DELTA, "timer does not decrement as expected");
  }

  /**
   * Test 1.1.3.
   */
  @Test
  public void testTimerFinishes() {
    float tickSize = initialTime;
    assertFalse(timer.tick(tickSize), "Timer should return false when it reaches zero.");
    assertFalse(timer.tick(1), "Timer should return false when below zero");
  }

  /**
   * Test 1.1.4.
   */
  @Test
  public void testResetTimer(){
    float tickSize = 1_000;
    timer.tick(tickSize);
    timer = new Timer(initialTime);
    assertEquals(initialTime, timer.getRemainingTime(), STANDARD_DELTA, "timer does not reset correctly");
  }

  /**
   * Test 1.1.5
   */
  @Test
  public void testDisplayRemainingTime(){
    Timer timer;
    String message;
    Map<Integer, String> map = new HashMap<>();
    map.put(10*60, "10:00");
    map.put(9*60, "09:00");
    map.put(60, "01:00");
    map.put(10, "00:10");
    map.put(9, "00:09");
    map.put(0, "00:00");
    map.put(-1, "00:00");

    for (var entry : map.entrySet()) {
      // Timer takes its initial time in ms, so multiply by 1000
      timer = new Timer(entry.getKey()*1000);
      message = String.format("Remaining time is not displayed properly when time is %ds", entry.getKey());
      assertEquals(entry.getValue(), timer.displayRemainingTime(), message);
    }
  }

  /**
   * Test 1.1.6.
   */
  @Test
  public void testIsRunning(){
    float tick1 = 1_000;
    float tick2 = 10_000;
    assertTrue(timer.isRunning(), "timer is not running initially");
    timer.tick(tick1);
    assertTrue(timer.isRunning(), "timer is not running when more than 0 seconds remain");
    timer.tick(tick2);
    assertFalse(timer.isRunning(),"timer is running after reaching 0 seconds");
    timer = new Timer(initialTime);
    assertTrue(timer.isRunning(),"timer is not running after being reset");
  }
}
