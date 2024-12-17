package io.github.unisim;

/**
 * A simple timer utility that can be updated on each render call.
 */
public class Timer {
  /**
   * The low-end of the timer, i.e. zero means finished.
   */
  public static float MIN_TIME = 0f;

  private float remainingTime;
  private float initialTime;
  private boolean hasFinished;
  private AcademicYear acadmemicYear;
  private Season season;
  private float seasonRemainingTime;


  /**
   * Create a new timer set to count down from an initial number of milliseconds.
   *
   * @param initialTime - The number of milliseconds before the timer ends
   */
  public Timer(float initialTime) {
    if (initialTime <= MIN_TIME) {
      initialTime = MIN_TIME;
    }
    this.initialTime = initialTime;
    remainingTime = initialTime;
    hasFinished = false;
    acadmemicYear = new AcademicYear(AcademicYear.FIRST_YEAR);
    season = Season.first();
    seasonRemainingTime = Season.LENGTH;
  }

  /**
   * __EXTENDED: INCLUDES YEARS/SEASONS HANDLING__ Removes a provided timestep from the counter and returns whether the
   * timer has stopped.
   *
   * @param deltaTime - the time in milliseconds to remove from the counter
   * @return - true if the timer is running and the time has been decremented, false otherwise.
   */
  public boolean tick(float deltaTime) {
    remainingTime -= deltaTime;
    seasonRemainingTime -= deltaTime;

    // If we're at the end of this season, reset the time and move on to the next
    if (seasonRemainingTime <= 0) {
      seasonRemainingTime = Season.LENGTH;
      // If this is the last season in this year, increment the academic year
      if (season == Season.last()) {
        acadmemicYear.increment();
      }
      season = season.next();
      // Add the quarterly income to the bank
      Bank.getInstance().credit(GameState.getInstance().getQuarterlyIncome());
    }

    if (remainingTime > 0) {
      return true;
    } else {
      remainingTime = 0;
      hasFinished = true;
      return false;
    }
  }

  public float getRemainingTime() {
    return remainingTime;
  }

  /**
   * __NEW: METHOD__ Returns the current point in the game, including Academic Year, Season and Remaining Time.
   *
   * @return the formatted progression
   */
  public String displayProgression() {
    return String.format("%s (%s)", displaySeason(), displayRemainingTime());
  }

  /**
   * Return the remaining time in a String representation.
   *
   * @return - remaining time in the form MM:SS
   */
  public String displayRemainingTime() {
    // get the number of minutes and seconds from the remaining time in milliseconds.
    int minutes = (int) ((remainingTime + 1000) / 60_000);
    int seconds = (int) Math.ceil(remainingTime / 1000 - 60 * minutes);

    return String.format("%02d:%02d", minutes, seconds);
  }

  /**
   * __NEW: METHOD__ Formats and returns the current Season within an Academic Year.
   *
   * @return the academic year / season
   */
  private String displaySeason() {
    return String.format("%s: %s", acadmemicYear, season);
  }

  /**
   * Return whether the timer is still running or has reached zero.
   *
   * @return - true if the timer is running, false if the remaining time has reached zero
   */
  public boolean isRunning() {
    return !hasFinished;
  }
}
