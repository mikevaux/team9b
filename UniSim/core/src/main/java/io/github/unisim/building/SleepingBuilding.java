package io.github.unisim.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class SleepingBuilding extends Building{
  public SleepingBuilding() {
    super();
    type = BuildingType.SLEEPING;
    name = "Student Accommodation";
    texture = new Texture(Gdx.files.internal("buildings/studentHousing.png"));
    textureScale = 0.108f;
    textureOffset = new Vector2(1.4f, -2.8f);
    size = new Point(11, 11);
  }
}
