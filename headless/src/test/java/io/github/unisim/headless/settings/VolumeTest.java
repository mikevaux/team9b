package io.github.unisim.headless.settings;

import io.github.unisim.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VolumeTest extends SettingsTest {
  private Settings settings;

  @BeforeEach
  public void boot() {
    settings = new Settings();
  }

  @Test
  public void testValidValue() {
    float valid = (Settings.MAX_VOLUME - Settings.MIN_VOLUME) / 2f;
    settings.setVolume(valid);
    assertEquals(valid, settings.getVolume(), STANDARD_DELTA, "Volume not set as expected");
  }

  @Test
  public void testMinValue() {
    settings.setVolume(Settings.MIN_VOLUME);
    assertEquals(Settings.MIN_VOLUME, settings.getVolume(), STANDARD_DELTA, "Volume should have been set to min value.");
    settings.setVolume(Settings.MIN_VOLUME - 0.1f);
    assertEquals(Settings.MIN_VOLUME, settings.getVolume(), STANDARD_DELTA, "Volume cannot go below min value!");
  }

  @Test
  public void testMaxValue() {
    settings.setVolume(Settings.MAX_VOLUME);
    assertEquals(Settings.MAX_VOLUME, settings.getVolume(), STANDARD_DELTA, "Volume should have been set to max value.");
    settings.setVolume(Settings.MAX_VOLUME + 0.1f);
    assertEquals(Settings.MAX_VOLUME, settings.getVolume(), STANDARD_DELTA, "Volume cannot go above max value!");
  }
}
