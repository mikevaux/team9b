package io.github.unisim.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.GameState;
import io.github.unisim.Point;

import java.text.NumberFormat;


/**
 * Represents a building that can be placed on the map.
 */
public abstract class Building {
  /**
   * The type of this building.
   */
  BuildingType type;
  /**
   * The name of the building to display when selected.
   */
  String name;
  /**
   * The cost of the building.
   */
  int cost;
  /**
   * The amount this building adds to quarterly income.
   */
  int incomeGeneration = 0;
  /**
   * The image to draw over the space the building occupies
   */
  Texture texture;
  /**
   * The (x, y) co-ordinates of the building on the map.
   * Note: We can save memory by storing only the top-left corner and the size of the building. This works as all
   * buildings are rectangular.
   */
  Point location;
  /**
   * The size (width, height) of the building in map tiles.
   * Note: {@link Point} has been used here, but ideally this would be a separate class for clarity.
   */
  Point size;
  /**
   * The scale of the image compared to the source file.
   */
  float textureScale;
  /**
   * The offset of the texture in grid tiles.
   */
  Vector2 textureOffset;
  /**
   * Whether to render a flipped variant of the building.
   */
  boolean flipped;

  /**
   * Sets attribute values common to all buildings when initialised.
   */
  public Building() {
    location = new Point();
    flipped = false;
  }

  /**
   * Abstracted mechanism of flipping a building. Flips and updates the size metrics accordingly.
   */
  public void flip() {
    this.setFlipped(!flipped);
    // Set x := y and y:= x here to invert
    this.setSize(size.y, size.x);
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public String displayCost() {
    NumberFormat f = NumberFormat.getInstance();
    f.setGroupingUsed(true);
    return GameState.getInstance().getCurrency() + f.format(cost);
  }

  public int getIncomeGeneration() {
    return incomeGeneration;
  }

  public Texture getTexture() {
    return texture;
  }

  public Point getSize() {
    return size;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public void setLocation(Point location) {
    this.location = location;
  }

  public void setSize(int x, int y) {
    this.size.x = x;
    this.size.y = y;
  }

  public Point getLocation() {
    return location;
  }

  public void setFlipped(boolean flipped) {
    this.flipped = flipped;
  }
}
