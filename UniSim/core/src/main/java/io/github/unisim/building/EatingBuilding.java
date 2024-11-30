package io.github.unisim.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.Point;

public class EatingBuilding extends Building{

  /**
   * Create a new building to display in the building menu and place in the world.
   *
   * @param texture       - The image to draw over the space the building occupies
   * @param textureScale  - The scale of the image compared to the source file
   * @param textureOffset - The offset of the texture in grid tiles
   * @param location      - The (x, y) co-ordinates of the building on the map
   * @param size          - The size (width, height) of the building in map tiles
   * @param flipped       - Whether to render a flipped variant of the building
   * @param name          - The name of the building to display when selected
   */
  public EatingBuilding(Texture texture, float textureScale, Vector2 textureOffset, Point location, Point size, Boolean flipped, String name) {
    super(texture, textureScale, textureOffset, location, size, flipped, name);
    this.type = BuildingType.EATING;
  }
}
