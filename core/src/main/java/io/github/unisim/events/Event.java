package io.github.unisim.events;

import io.github.unisim.ui.BuildingMenu;

/**
 * __NEW: WHOLE CLASS__ Provides attributes and base methods for all UniSim {@link Event}s
 */
abstract public class Event {
  private final String title;
  private final String body;
  private String iconFilename;
  private int duration;
  private float timer;
  private static final int eventDuration = 30;

  /**
   * Creates a new {@link Event} with just a title and body.
   *
   * @param title the title of the Event
   * @param body the body of the Event
   */
  public Event(String title, String body) {
    this.title = title;
    this.body = body;
    this.duration = eventDuration;
    this.timer = 0;
  }
  /**
   * Creates a new {@link Event} with a title, body and icon.
   *
   * @param title the title of the Event
   * @param body the body of the Event
   * @param iconFilename the icon for the Event
   */
  public Event(String title, String body, String iconFilename) {
    this.title = title;
    this.body = body;
    this.iconFilename = iconFilename;
    this.duration = eventDuration;
    this.timer = 0;
  }

  /**
   * getter method to check if an event is over.
   *
   * @param delta the time difference since the last call
   *
   * @return true if event is over, false otherwise
   */
  public boolean eventOver(float delta){
    timer += delta;
    return timer > duration;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public String getIconFilename() {
    return iconFilename;
  }

  /**
   * runs the current event.
   *
   * @param delta the time since the last call
   */
  public void runEvent(float delta) {
  }

  /**
   * runs anything required to be done right at the start of an event.
   */
  public void doBefore(){}

  /**
   * runs anything required to be done right at the end of an event.
   */
  public void doAfter(){}
}
