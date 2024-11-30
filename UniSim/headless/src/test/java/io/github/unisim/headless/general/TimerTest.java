package io.github.unisim.headless.general;

import io.github.unisim.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimerTest extends GeneralTest{
  static Timer timer;
  static float initialTime = 10_000;

  @BeforeEach
  public void boot(){timer = new Timer(initialTime);}


  @Test
  public void testInitialTime(){
    assertEquals(initialTime, timer.getTime(), STANDARD_DELTA, "time is not set as expected");
  }


  @Test
  public void testTimerDecrements(){
    float tickSize1 = 1_000;
    float tickSize2 = 10_000;
    float newTime1 = initialTime - tickSize1;
    timer.tick(tickSize1);
    assertEquals(newTime1, timer.getTime(), STANDARD_DELTA, "timer does not decrement as expected");
    assertFalse(timer.tick(tickSize2), "timer does not return false when below zero");
  }


  @Test
  public void testResetTimer(){
    float tickSize = 1_000;
    timer.tick(tickSize);
    timer.reset();
    assertEquals(initialTime, timer.getTime(), STANDARD_DELTA, "timer does not reset correctly");
  }


  @Test
  public void testGetRemainingTime(){
    float tick1 = 4000;
    float tick2 = 10_000;
    timer.tick(tick1);
    assertEquals("00:06", timer.getRemainingTime(), "remaining time is not properly output");
    timer.tick(tick2);
    assertEquals("00:00", timer.getRemainingTime(), "remaining time is not output properly when time = 0");
  }


  @Test
  public void testIsRunning(){
    float tick1 = 1_000;
    float tick2 = 10_000;
    assertTrue(timer.isRunning(), "timer is not running initially");
    timer.tick(tick1);
    assertTrue(timer.isRunning(), "timer is not running when more than 0 seconds remain");
    timer.tick(tick2);
    assertFalse(timer.isRunning(),"timer is running after reaching 0 seconds");
    timer.reset();
    assertTrue(timer.isRunning(),"timer is not running after being reset");
  }

}
