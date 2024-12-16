package io.github.unisim.events;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.achievements.OnFire;
import io.github.unisim.building.Building;
import io.github.unisim.BuildingManager;

/**
 * __NEW: WHOLE CLASS__ Negative event that removes all bonuses of the 'on fire' building.
 */
public class Fire extends Event {
  private final BuildingManager buildingManager;
  private Texture fireTexture;

  public Fire(BuildingManager buildingManager) {
    super("Event: Fire", "Oh no! a building caught fire!\nreplace it to save satisfaction", "events/eventWarning.png");
    this.buildingManager = buildingManager;
    this.fireTexture = null;
  }

  @Override
  public void doBefore(){
    // Gets a random building (filtered internally to certain types)
    Building fireBuilding = buildingManager.getRandomBuilding();
    // Run only if a building has been placed (and therefore got)
    if (fireBuilding != null){
      // Store the name of the burned building as a target to rebuild
      OnFire.setBuildingToBuild(fireBuilding.getName());
      fireBuilding.setOnFire(true);
      // Decrease the counter of the building type by one
      buildingManager.decrementCounter(fireBuilding);
    }
  }
}
