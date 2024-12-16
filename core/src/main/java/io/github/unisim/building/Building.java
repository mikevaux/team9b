package io.github.unisim.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.unisim.GameState;
import io.github.unisim.Point;

import java.text.NumberFormat;


/**
 * __REFACTORED TO ADD CHILD CLASSES__ Represents a building that can be placed on the map.
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
   * The filename of the building's asset.
   */
  String filename;
  /**
   * The cached Texture to draw over the space the building occupies
   */
  Texture textureCache;
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
   * Whether the building is on fire.
   */
  boolean onFire = false;

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

  /**
   * Makes and caches the texture for this building.
   */
  private void cacheTexture() {
    String dir = "buildings/";
    if (onFire) {
      dir += "on-fire/";
    }
    textureCache = new Texture(dir + filename);
  }

  public BuildingType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  /**
   * Formats as currency and returns the cost of this building.
   *
   * @return the formatted string, e.g. £50,000
   */
  public String displayCost() {
    NumberFormat f = NumberFormat.getInstance();
    f.setGroupingUsed(true);
    return GameState.getInstance().getCurrency() + f.format(cost);
  }

  public int getIncomeGeneration() {
    return incomeGeneration;
  }

  /**
   * Gets (after caching, if needed) the {@link Texture} for this building.
   *
   * @return the {@link Texture} for this building
   */
  public Texture getTexture() {
    // Cache this to avoid generating it multiple times
    if (textureCache == null) {
      cacheTexture();
    }
    return textureCache;
  }

  public Point getSize() {
    return size;
  }

  public float getTextureScale() {
    return textureScale;
  }

  public Vector2 getTextureOffset() {
    return textureOffset;
  }

  public boolean isFlipped() {
    return flipped;
  }

  public boolean isOnFire() {
    return onFire;
  }

  /**
   * Manually set the texture cache to null so that it is recreated next time `getTexture()` is called.
   */
  private void invalidateTextureCache() {
    textureCache = null;
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

  /**
   * Sets the `onFire` property, then invalidates the `textureCache`, so that it is remade next time.
   *
   * @param onFire whether the building is on fire
   */
  public void setOnFire(boolean onFire) {
    this.onFire = onFire;
    this.invalidateTextureCache();
  }
}
