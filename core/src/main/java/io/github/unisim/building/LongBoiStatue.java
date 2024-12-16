package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

/**
 * __NEW: WHOLE CLASS__ A Long Boi Statue Building.
 */
public class LongBoiStatue extends EventBuilding {
  public LongBoiStatue() {
    super();
    name = "Longboi Statue";
    cost = 50_000;
    filename = "longboi.png";
    textureScale = 0.005f;
    textureOffset = new Vector2(0.5f, 0.15f);
    size = new Point(2, 2);
  }
}
