package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

/**
 * __NEW: WHOLE CLASS__ A Learning Building.
 */
public class LearningBuilding extends Building {
  public LearningBuilding() {
    super();
    type = BuildingType.LEARNING;
    name = "Library";
    cost = 500_000;
    filename = "library.png";
    textureScale = 0.0075f;
    textureOffset = new Vector2(1.8f, -4.6f);
    size = new Point(20, 12);
  }
}
