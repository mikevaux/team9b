package io.github.unisim.achievements;

import io.github.unisim.messages.MessageHandler;

/**
 * __NEW: WHOLE CLASS__ Provides an abstracted API for handling UniSim Achievements.
 *
 * @see io.github.unisim.messages.MessageHandler which shows messages associated with these events
 */
public class AchievementsHandler {
  private final MessageHandler messageHandler;
  private final FitnessFreak fitnessFreak;
  private final AreYouStillWatching areYouStillWatching;
  private final DoYouSmellCarrots doYouSmellCarrots;
  private final DuckDuckDuck duckDuckDuck;
  private final HowDidWeGetHere howDidWeGetHere;
  private final OnFire onFire;
  private final Overachiever overachiever;
  private final PressStartToPlay pressStartToPlay;
  private static int achievementsGained = 0;


  /**
   * Creates a new AchievementsHandler, with a shared MessageHandler.
   *
   * @param messageHandler the shared {@link MessageHandler} instance
   */
  public AchievementsHandler(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;

    this.areYouStillWatching = new AreYouStillWatching();
    this.doYouSmellCarrots = new DoYouSmellCarrots();
    this.duckDuckDuck = new DuckDuckDuck();
    this.fitnessFreak = new FitnessFreak();
    this.howDidWeGetHere = new HowDidWeGetHere();
    this.onFire = new OnFire();
    this.overachiever = new Overachiever();
    this.pressStartToPlay = new PressStartToPlay();

  }

  /**
   * Shows the given {@link Achievement}, by interfacing with the {@link MessageHandler}.
   * Sets the static boolean alreadyDisplayed for the appropriate achievements class too true.
   *
   * @param achievement the Achievement to show
   */
  public void showAchievement(Achievement achievement) {
    achievementsGained += 1;
    messageHandler.showMessage(achievement.getTitle(), achievement.getBody(), achievement.getIconFilename());
    switch (achievement.getTitle()){
      case "Fitness Freak!":
        FitnessFreak.setAlreadyDisplayed(true);
        break;
      case "Are You Still Watching?":
        AreYouStillWatching.setAlreadyDisplayed(true);
        break;
      case "Duck..Duck..Duck??!":
        DuckDuckDuck.setAlreadyDisplayed(true);
        break;
      case "How Did We Get Here?":
        HowDidWeGetHere.setAlreadyDisplayed(true);
        break;
      case "On Fire!":
        OnFire.setAlreadyDisplayed(true);
        break;
      case "Overachiever!":
        Overachiever.setAlreadyDisplayed(true);
        break;
      case "Press Start To Play.":
        PressStartToPlay.setAlreadyDisplayed(true);
        break;
    }
  }

  /**
   * Checks the status of certain achievements every time render is run.
   *
   * @param delta the time since the last render call
   */
  public void checkAchievements(float delta){
    areYouStillWatching.checkAreYouStillWatching(delta);
    doYouSmellCarrots.checkDoYouSmellCarrots();
    overachiever.checkOverachiever();
  }


  /**
   * Checks if any of the achievements have been completed every time render is run.
   *
   * @param delta the time since the last render call
   */
  public void displayAchievements(float delta){
    checkAchievements(delta);
    if(FitnessFreak.isDisplay()){
      showAchievement(fitnessFreak);
      FitnessFreak.setDisplay(false);
    }
    if(AreYouStillWatching.isDisplay()){
      showAchievement(areYouStillWatching);
      AreYouStillWatching.setDisplay(false);
    }
//    if(this.doYouSmellCarrots.display()){
//      showAchievement(doYouSmellCarrots);
//    }
    if(DuckDuckDuck.isDisplay()){
      showAchievement(duckDuckDuck);
      DuckDuckDuck.setDisplay(false);
    }
    if(HowDidWeGetHere.isDisplay()){
      showAchievement(howDidWeGetHere);
      HowDidWeGetHere.setDisplay(false);
    }
    if(OnFire.isDisplay()){
      showAchievement(onFire);
      OnFire.setDisplay(false);
    }
    if(Overachiever.isDisplay()){
      showAchievement(overachiever);
      Overachiever.setDisplay(false);
    }
    if(PressStartToPlay.isDisplay()){
      showAchievement(pressStartToPlay);
      PressStartToPlay.setDisplay(false);
    }
//    if(this.sticksAndStones.display()){
//      showAchievement(sticksAndStones);
//    }
  }

  public static int getAchievementsGained() {
    return achievementsGained;
  }
}
