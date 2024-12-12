package io.github.unisim.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class LearningBuilding extends Building {
  public LearningBuilding() {
    super();
    type = BuildingType.LEARNING;
    name = "Library";
    cost = 500_000;
    texture = new Texture(Gdx.files.internal("buildings/library.png"));
    textureScale = 0.0075f;
    textureOffset = new Vector2(1.8f, -4.6f);
    size = new Point(20, 12);
  }
}
