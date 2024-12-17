package io.github.unisim.headless.settings;

import io.github.unisim.Settings;
import io.github.unisim.headless.AbstractHeadlessGdxTest;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractSettingsTest extends AbstractHeadlessGdxTest {
  Settings settings;

  @BeforeEach
  public void boot() {
    settings = new Settings();
  }
}
