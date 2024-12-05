package io.github.unisim.events;

import com.badlogic.gdx.graphics.Texture;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingManager;
import io.github.unisim.ui.BuildingMenu;

/**
 * Negative event that removes all bonuses of the 'on fire' building.
 */
public class Fire extends Event {
  private Texture fireTexture;

  public Fire() {
    super("Event: Fire", "Oh no! a building caught fire! replace it to save satisfaction", "events/eventWarning.png");
    this.fireTexture = null;
  }

  @Override
  public void doBefore(){
    Building fireBuilding = BuildingManager.getRandomBuilding(); //gets a random building
    //runs only if a building has been placed (and therefore got)
    if (fireBuilding != null){
      String type = fireBuilding.getName();
      //replace the texture of the building with an 'on fire' version
      switch (type){
        case "Basketball Court":
          fireTexture = new Texture("fireBuildings/basketballCourt.png");
          break;
        case "Library":
          fireTexture = new Texture("fireBuildings/library.png");
          break;
        case "Canteen":
          fireTexture = new Texture("fireBuildings/restaurant.png");
          break;
        case "Stadium":
          fireTexture = new Texture("fireBuildings/stadium.png");
          break;
        case "Student Accommodation":
          fireTexture = new Texture("fireBuildings/studentHousing.png");
          break;
      }
      fireBuilding.setTexture(fireTexture);
      BuildingManager.decrementCounter(fireBuilding); //decrease the counter of the building type by one
    }
  }

}
