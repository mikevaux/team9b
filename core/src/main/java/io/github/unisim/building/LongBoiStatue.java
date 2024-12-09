package io.github.unisim.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class LongBoiStatue extends EventBuilding {
  public LongBoiStatue() {
    super();
    texture = new Texture(Gdx.files.internal("events/longboi.png"));
    name = "Longboi Statue";
    textureScale = 0.005f;
    textureOffset = new Vector2(0.5f, 0.15f);
    size = new Point(2, 2);
  }
}
