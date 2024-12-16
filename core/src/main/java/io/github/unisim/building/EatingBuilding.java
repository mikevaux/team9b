package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

/**
 * __NEW: WHOLE CLASS__ An Eating Building.
 */
public class EatingBuilding extends Building {
  public EatingBuilding() {
    super();
    type = BuildingType.EATING;
    name = "Canteen";
    cost = 30_000;
    filename = "restaurant.png";
    textureScale = 0.01f;
    textureOffset = new Vector2(0.35f, -0.9f);
    size = new Point(3, 3);
  }
}
