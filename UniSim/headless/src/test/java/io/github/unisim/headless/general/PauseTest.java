package io.github.unisim.headless.general;

import io.github.unisim.Timer;
import io.github.unisim.ui.GameScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PauseTest extends GeneralTest{
  static GameScreen screen;
  static Timer timer;
  float time2;
  int timeDifference = 3_000;

  @BeforeAll
  public static void boot(){screen = new GameScreen();}

  @Test
  public void testPause(){
    javax.swing.Timer pauseTimer = new javax.swing.Timer(timeDifference, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        time2 = timer.getRemainingTime();
      }
    });
    pauseTimer.setRepeats(false); // Only execute once
    screen.pause();
    float time1 = timer.getRemainingTime();
    pauseTimer.start();
    assertEquals(time1, time2, STANDARD_DELTA, "timer does not pause properly");
  }


  @Test
  public void testTimer(){
    javax.swing.Timer timerTimer = new javax.swing.Timer(timeDifference, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        time2 = timer.getRemainingTime();
      }
    });
    timerTimer.setRepeats(false); // Only execute once
    float time1 = timer.getRemainingTime();
    timerTimer.start();
    assertEquals(time1, time2 + timeDifference, STANDARD_DELTA, "timer does not count down properly");
  }
}
