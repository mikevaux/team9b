package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class Stadium extends RecreationBuilding {
  public Stadium() {
    super();
    name = "Stadium";
    cost = 1_000_000;
    filename = "buildings/stadium.png";
    textureScale = 0.0025f;
    textureOffset = new Vector2(1f, 1f);
    size = new Point(12, 16);
  }
}
