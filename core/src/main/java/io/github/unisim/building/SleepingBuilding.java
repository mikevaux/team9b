package io.github.unisim.building;

import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

/**
 * __NEW: WHOLE CLASS__ A Sleeping Building.
 */
public class SleepingBuilding extends Building{
  public SleepingBuilding() {
    super();
    type = BuildingType.SLEEPING;
    name = "Student Accommodation";
    cost = 100_000;
    incomeGeneration = 50_000;
    filename = "studentHousing.png";
    textureScale = 0.108f;
    textureOffset = new Vector2(1.4f, -2.8f);
    size = new Point(11, 11);
  }
}
