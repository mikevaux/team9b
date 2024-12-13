package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class BasketballCourt extends RecreationBuilding {
  public BasketballCourt() {
    super();
    name = "Basketball Court";
    cost = 20_000;
    filename = "basketballCourt.png";
    textureScale = 0.0025f;
    textureOffset = new Vector2(1f, -2.4f);
    size = new Point(6, 9);
  }
}
