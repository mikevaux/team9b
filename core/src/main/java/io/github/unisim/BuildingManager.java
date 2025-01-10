package io.github.unisim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import io.github.unisim.achievements.*;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import io.github.unisim.building.EventBuilding;
import io.github.unisim.building.LongBoiStatue;
import io.github.unisim.events.LongboiDay;
import io.github.unisim.satisfaction.SatisfactionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * __EXTENDED: NEW METHODS__ Manages the buildings placed in the world and methods common to all buildings.
 */
public class BuildingManager {
  // create a list of buildings which will be sorted by a height metric derived from
  // the locations of the corners of the buildings.
  private ArrayList<Building> buildings = new ArrayList<>();
  private Map<BuildingType, Integer> buildingCounts = new HashMap<>();
  private Matrix4 isoTransform;
  private Building previewBuilding;
  private SatisfactionHandler satisfactionHandler;

  /**
   * Contains the IDs of all buildable tiles.
   */
  private final Set<Integer> buildableTiles;

  public BuildingManager(Matrix4 isoTransform) {
    this.isoTransform = isoTransform;
    // Set was used to make searching more efficient.
    buildableTiles = Stream.of(14, 15).collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Registers the instance of the {@link SatisfactionHandler} with this {@link BuildingManager}. Both classes require
   * a reference to each other, so without a large refactoring, this provides a means of achieving that.
   *
   * @param satisfactionHandler the instance of {@link SatisfactionHandler}
   */
  public void registerSatisfactionHandler(SatisfactionHandler satisfactionHandler) {
    this.satisfactionHandler = satisfactionHandler;
  }

  /**
   * Determines if a region on the map is composed solely of buildable tiles.
   *
   * @param btmLeft - The co-ordinates of the bottom left corner of the search region
   * @param topRight - The co-ordinates of the top right corner of the search region
   * @param tileLayer - A reference to the map layer containing all terrain tiles
   * @return - true if the region is made solely of buildable tiles, false otherwise
   */
  public boolean isBuildable(Point btmLeft, Point topRight, TiledMapTileLayer tileLayer) {
    boolean buildable = true;
    // we iterate over each tile within the search region and check
    // for any non-buildable tiles.
    for (int x = btmLeft.x; x <= topRight.x && buildable; x++) {
      for (int y = btmLeft.y; y <= topRight.y && buildable; y++) {
        Cell currentCell = tileLayer.getCell(x, y);
        if (currentCell == null) {
          buildable = false;
          continue;
        }

        TiledMapTile currentTile = currentCell.getTile();
        if (!tileBuildable(currentTile)) {
          buildable = false;
        }
      }
    }
    if (!buildable) {
      return false;
    }

    // Next, iterate over the current buildings to see if any intersect the new building
    for (Building building : buildings) {
      // Use the seperating axis theorem to detect building overlap
      if (!(building.getLocation().x > topRight.x
          || building.getLocation().x + building.getSize().x - 1 < btmLeft.x
          || building.getLocation().y > topRight.y
          || building.getLocation().y + building.getSize().y - 1 < btmLeft.y)
      ) {
        if (building == previewBuilding) {
          continue;
        }
        buildable = false;
        break;
      }
    }

    return buildable;
  }



  /**
   * Getter that picks a random building (not including {@link EventBuilding}s or preview buildings) from the list of buildings.
   *
   * @return - random Building from buildings, null if empty
   */
  public Building getRandomBuilding(){
    int len = filterBuildings().size();
    if (len != 0){
      int randomPosition = (int)(Math.random() * len);
      return filterBuildings().get(randomPosition);
    }
    return null;
  }

  /**
   * Helper method that determines if the provided tile may be built on.
   *
   * @param tile - A reference to a tile on the terrain layer of the map.
   * @return - true if the tile is buildable, false otherwise
   */
  private boolean tileBuildable(TiledMapTile tile) {
    return buildableTiles.contains(tile.getId());
  }

  /**
   * Draws each building from the building list onto the map.
   *
   * @param batch - the SpriteBatch in which to draw
   */
  public void render(SpriteBatch batch) {
    for (Building building : buildings) {
      drawBuilding(building, batch);
    }
  }

  /**
   * Places a building without building it, for previewing.
   *
   * @param building a reference to the building object being placed
   */
  public void previewBuilding(Building building) {
    placeBuilding(building);
  }

  /**
   * Places a building and updates the context.
   *
   * @param building a reference to the building object being placed
   */
  public void buildBuilding(Building building) {
    placeBuilding(building);
    updateCounters(building);
    satisfactionHandler.setChanges(true);
  }

  /**
   * Handle placement of a building into the world by determining
   * the correct draw order and updating the building counters.
   *
   * @param building - A reference to a building object to be placed
   */
  private void placeBuilding(Building building) {
    // Insert the building into the correct place in the arrayList to ensure it
    // gets rendered in top-down order
    // Start by calculating the 'height' values for the left and right corners of the new building
    // where height is the taxi-cab distance from the top of the map
    int buildingHeightLeftSide = building.getLocation().y - building.getLocation().x;
    int buildingHeightRightSide = buildingHeightLeftSide + building.getSize().y - building.getSize().x + 1;
    Point leftCorner = building.getLocation();

    // Move up the array, until the pointer is in the correct place for the new building so the
    // array is sorted by height
    int i = 0;
    while (i < buildings.size()) {
      Building other = buildings.get(i);
      int otherHeightLeftSide = other.getLocation().y - other.getLocation().x;
      // Calculate the taxi-cab distance between the new building's left corner and the other
      // building's right corner
      int leftDistance = Math.abs(leftCorner.x - other.getLocation().x - other.getSize().x + 1)
          + Math.abs(leftCorner.y - other.getLocation().y - other.getSize().y + 1);
      // If the distance is small, compare the height of the new buildin'g left corner to the
      // height of the other buildings right corner
      if (leftDistance < Math.min(building.getSize().x + building.getSize().y, other.getSize().x + other.getSize().y)) {
        int otherHeightRightSide = otherHeightLeftSide + other.getSize().y - other.getSize().x + 1;
        if (otherHeightRightSide > buildingHeightLeftSide) {
          i++;
          continue;
        } else {
          break;
        }
      }
      // Otherwise, compare the distance of the new building's right corner to the other building's
      // left corner
      if (otherHeightLeftSide > buildingHeightRightSide) {
        i++;
      } else {
        break;
      }
    }

    buildings.add(i, building);
  }

  /**
   * Creates a counter for the building's type if it is the first to be placed,
   * otherwise increments the counter for that type by one.
   *
   * @param building - A reference to the building object that was placed
   */
  private void updateCounters(Building building) {
    // Building Counts does not include EventBuildings
    if (building instanceof EventBuilding) {
      return;
    }

    if (!buildingCounts.containsKey(building.getType())) {
      buildingCounts.put(building.getType(), 0);
    }
    buildingCounts.put(building.getType(), buildingCounts.get(building.getType()) + 1);

    FitnessFreak.checkBuildings(buildingCounts);
    HowDidWeGetHere.checkBuildings(buildingCounts);
  }

  /**
   * decrements the counter of the buildings type by one.
   *
   * @param building - A reference to the building object that was placed
   */
  public void decrementCounter(Building building){
    buildingCounts.put(building.getType(), buildingCounts.get(building.getType()) - 1);
  }

  /**
   * Returns the number of buildings of a certain type that have been placed
   * in the world.
   *
   * @param type - The type of building
   * @return - The number of buildings of that type that have been placed
   */
  public int getBuildingCount(BuildingType type) {
    if (!buildingCounts.containsKey(type)) {
      return 0;
    }
    return buildingCounts.get(type);
  }

  /**
   * Filters the array buildings, removing any event or preview buildings.
   *
   * @return an arraylist of all placed buildings minus event and preview buildings.
   */
  public ArrayList<Building> filterBuildings() {
    ArrayList<Building> builtBuildings = new ArrayList<>();
    for (Building building: buildings){
      if (!(building == previewBuilding) && !(building.getType() == BuildingType.EVENT)){
        builtBuildings.add(building);
      }
    }
    return builtBuildings;
  }

  public int getNumberOf(BuildingType type){
    int counter = 0;
    for (Building building : buildings){
      if (building.getType() == type){
        counter += 1;
      }
    }
    return counter;
  }

  public boolean longboiStatuePlaced() {
    for (Building building : buildings) {
      if (building instanceof LongBoiStatue) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Building> getTypeBuildings(BuildingType type) {
    ArrayList<Building> typeBuildings = new ArrayList<>();
    for (Building building: buildings){
      if (building.getType() == type && building != previewBuilding){
        typeBuildings.add(building);
      }
    }
    return typeBuildings;
  }

  /**
   * Sets the building to render as a 'preview' on the map prior to placement.

   * @param previewBuilding - The building to draw as a preview
   */
  public void setPreviewBuilding(Building previewBuilding) {
    if (this.previewBuilding != null) {
      buildings.remove(this.previewBuilding);
    }
    this.previewBuilding = previewBuilding;
    if (previewBuilding != null) {
      previewBuilding(previewBuilding);
    }
  }

  /**
   * Draw the building texture at the position of the mouse cursor
   * when building mode is enabled.

   * @param building - The building to draw under the mouse cursor
   * @param batch - the SpriteBatch to draw into
   */
  public void drawBuilding(Building building, SpriteBatch batch) {
    Vector3 btmLeftPos = new Vector3(
        (float) building.getLocation().x + (
          building.isFlipped() ? building.getTextureOffset().x : building.getTextureOffset().x
        ),
        (float) building.getLocation().y + (
          building.isFlipped() ? building.getTextureOffset().y : building.getTextureOffset().y
        ),
        0f
    );
    Vector3 btmRightPos = new Vector3(btmLeftPos).add(new Vector3(building.getSize().x - 1, 0f, 0f));
    btmLeftPos.mul(isoTransform);
    btmRightPos.mul(isoTransform);
    batch.draw(
      building.getTexture(),
        btmLeftPos.x, btmRightPos.y,
        building.getTexture().getWidth() * building.getTextureScale(),
        building.getTexture().getHeight() * building.getTextureScale(),
        0, 0, building.getTexture().getWidth(), building.getTexture().getHeight(),
      building.isFlipped(), false
    );
  }
}
