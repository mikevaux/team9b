package io.github.unisim.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class Stadium extends RecreationBuilding {
  public Stadium() {
    super();
    texture = new Texture(Gdx.files.internal("buildings/stadium.png"));
    textureScale = 0.0025f;
    textureOffset = new Vector2(1f, 1f);
    size = new Point(12, 16);
    name = "Stadium";
  }
}
