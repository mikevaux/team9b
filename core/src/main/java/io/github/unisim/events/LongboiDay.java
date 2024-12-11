package io.github.unisim.events;
import io.github.unisim.ui.BuildingMenu;

/**
 * Positive event that lets you place a statue that gives you a satisfaction bonus.
 */
public class LongboiDay extends Event {
  private static BuildingMenu menu;

  private static boolean longboiDay = false;
  public LongboiDay(BuildingMenu menu) {
    super("Event: Longboi Day", "Place the Longboi statue for a satisfaction bonus", "events/eventWarning.png");
    LongboiDay.menu = menu;
  }

  @Override
  public void doBefore(){
    //add the statue to the menu bar
    menu.setLongBoiVisibility(true);
  }

  public static void setInvisible(){
    //remove the statue from the menu bar once placed
    menu.setLongBoiVisibility(false);
  }

  @Override
  public void doAfter(){
    //remove the statue from the menu bar once the event is over
    setInvisible();
  }
}
