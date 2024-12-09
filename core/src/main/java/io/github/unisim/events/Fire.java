package io.github.unisim.events;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.achievements.OnFire;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingManager;

/**
 * Negative event that removes all bonuses of the 'on fire' building.
 */
public class Fire extends Event {
  private final BuildingManager buildingManager;
  private Texture fireTexture;

  public Fire(BuildingManager buildingManager) {
    super("Event: Fire", "Oh no! a building caught fire! replace it to save satisfaction", "events/eventWarning.png");
    this.buildingManager = buildingManager;
    this.fireTexture = null;
  }

  @Override
  public void doBefore(){
    Building fireBuilding = buildingManager.getRandomBuilding(); //gets a random building
    //runs only if a building has been placed (and therefore got)
    if (fireBuilding != null){
      String type = fireBuilding.getName();
      //replace the texture of the building with an 'on fire' version
      switch (type){
        case "Basketball Court":
          fireTexture = new Texture("fireBuildings/basketballCourt.png");
          OnFire.setBuildingToBuild("Basketball Court");
          break;
        case "Library":
          fireTexture = new Texture("fireBuildings/library.png");
          OnFire.setBuildingToBuild("Library");
          break;
        case "Canteen":
          fireTexture = new Texture("fireBuildings/restaurant.png");
          OnFire.setBuildingToBuild("Canteen");
          break;
        case "Stadium":
          fireTexture = new Texture("fireBuildings/stadium.png");
          OnFire.setBuildingToBuild("Stadium");
          break;
        case "Student Accommodation":
          fireTexture = new Texture("fireBuildings/studentHousing.png");
          OnFire.setBuildingToBuild("Student Accommodation");
          break;
      }
      fireBuilding.setTexture(fireTexture);
      buildingManager.decrementCounter(fireBuilding); //decrease the counter of the building type by one
    }
  }

}
