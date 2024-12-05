package io.github.unisim.events;

import io.github.unisim.Timer;
import io.github.unisim.messages.MessageHandler;
import io.github.unisim.ui.BuildingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Provides an abstracted API for handling UniSim Events.
 *
 * @see io.github.unisim.messages.MessageHandler which shows messages associated with these events
 */
public class EventsHandler {
  private final BuildingMenu menu;
  private final MessageHandler messageHandler;
  private final HashMap<String, Event> events;
  private final ArrayList<String> order;
  boolean event1;
  boolean event2;
  boolean event3;
  Event currentEvent;
  boolean runPre = true;
  boolean runPost = true;
  /**
   * Creates a new EventsHandler, with a shared MessageHandler.
   *
   * @param messageHandler the shared {@link MessageHandler} instance
   */
  public EventsHandler(MessageHandler messageHandler, BuildingMenu menu) {
    this.messageHandler = messageHandler;
    this.menu = menu;
    events = new HashMap<String, Event>();
    events.put("fire",new Fire());
    events.put("duck", new LongboiDay(menu));
    events.put("winter", new WinterHolidays());
    order = randomiseEventOrder();
    event1 = true;
    event2 = true;
    event3 = true;
    currentEvent = null;
  }

  /**
   * Shows the given {@link Event}, by interfacing with the {@link MessageHandler}.
   *
   * @param event the Event to show
   */
  public void showEvent(Event event) {
    messageHandler.showMessage(event.getTitle(), event.getBody(), event.getIconFilename());
  }

  private ArrayList<String> randomiseEventOrder(){
    ArrayList<String> events= new ArrayList<String>();
    events.add("fire");
    events.add("duck");
    events.add("winter");
    ArrayList<String> randomOrder = new ArrayList<String>();
    Random ran = new Random();
    int x = ran.nextInt(3);
    randomOrder.add(events.get(x));
    events.remove(x);
    x = ran.nextInt(2);
    randomOrder.add(events.get(x));
    events.remove(x);
    randomOrder.add(events.get(0));
    return randomOrder;
  }

  public void checkEvents(float delta, Timer timer){
    if (timer.getRemainingTime() < 295_000 && event1){
      event1 = false;
      currentEvent = this.events.get(this.order.get(0));
      showEvent(currentEvent);
      setPreAndPost();
    }else if (timer.getRemainingTime() < 285_000 && event2){
      event2 = false;
      currentEvent = this.events.get(this.order.get(1));
      showEvent(currentEvent);
      setPreAndPost();
    }else if (timer.getRemainingTime() < 275_000 && event3){
      event3 = false;
      currentEvent = this.events.get(this.order.get(2));
      showEvent(currentEvent);
      setPreAndPost();
    }
  }

  public void setPreAndPost(){
    runPre = true;
    runPost = true;
  }

  public void runCurrentEvent(float delta){
    if (currentEvent != null) {
      if(runPre){
        currentEvent.doBefore();
        runPre = false;
      }
      if (!currentEvent.eventOver(delta)) {
        currentEvent.runEvent(delta);
      }else if(runPost){
        currentEvent.doAfter();
        runPost = false;
      }
    }
  }
}

