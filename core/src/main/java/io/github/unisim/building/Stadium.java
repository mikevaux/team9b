package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

/**
 * __NEW: WHOLE CLASS__ A Stadium Building.
 */
public class Stadium extends RecreationBuilding {
  public Stadium() {
    super();
    name = "Stadium";
    cost = 750_000;
    incomeGeneration = 250_000;
    filename = "stadium.png";
    textureScale = 0.0025f;
    textureOffset = new Vector2(1f, 1f);
    size = new Point(12, 16);
  }
}
