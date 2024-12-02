package io.github.unisim.events;

import io.github.unisim.ui.GameScreen;

public class WinterHolidays extends Event {
  public WinterHolidays() {
    super("Winter Holidays", "Happy Holidays", "events/eventWarning.png");
    GameScreen.winterHolidays = true;
  }
}
