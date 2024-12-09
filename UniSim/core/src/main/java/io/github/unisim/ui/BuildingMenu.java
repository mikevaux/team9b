package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.Point;
import io.github.unisim.building.*;
import io.github.unisim.events.LongboiDay;
import io.github.unisim.world.World;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Menu used to place buildings in the world by clicking and dragging them
 * from the list onto the map.
 */
@SuppressWarnings({"MemberName", "AbbreviationAsWordInName"})
public class BuildingMenu {
  private World world;
  private ShapeActor bar = new ShapeActor(GameState.UISecondaryColour);
  private Table table;
  private Label buildingInfoLabel = new Label(
    "", new Skin(Gdx.files.internal("ui/uiskin.json"))
  );
  private Table buildingInfoTable = new Table();
  private Image longboiImage;

  /**
   * Create a Building Menu and attach its actors and components to the provided stage.
   * Also handles drawing buildings and their flipped variants
   *
   * @param stage - The stage on which to draw the menu.
   */
  public BuildingMenu(Stage stage, World world) {
    this.world = world;
    table = new Table();
    ArrayList<Building> buildings = new ArrayList<>();

    buildings.add(new EatingBuilding());
    buildings.add(new LearningBuilding());
    buildings.add(new BasketballCourt());
    buildings.add(new Stadium());
    buildings.add(new SleepingBuilding());
    buildings.add(new LongBoiStatue());

    // Add buildings to the table
    for (Building building : buildings) {
      Image image = new Image(building.getTexture());

      // Event buildings are only available during the relevant event
      if (building instanceof EventBuilding) {
        image.setVisible(false);

        // Cache the LongBoi image for retrieval
        if (building instanceof LongBoiStatue) {
          longboiImage = image;
        }
      }
      table.add(image);

      image.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          try {
            // Use reflection to create a new instance of the respective class
            world.selectedBuilding = building.getClass().getConstructor().newInstance();
            // Update the label to provide some helpful pointers to the player
            buildingInfoLabel.setText(world.selectedBuilding.getName() + " - Press 'R' to rotate, 'Esc' to cancel");
          } catch (NoSuchMethodException e) {
            System.out.println("Error: could not find public nullary constructor for " + building.getClass().getName());
          } catch (Exception e) {
            System.out.println("Something went wrong when creating a new building");
          }
        }
      });
    }

    buildingInfoTable.add(buildingInfoLabel).expandX().align(Align.center);

    stage.addActor(bar);
    stage.addActor(table);
    stage.addActor(buildingInfoTable);
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.
   *
   * @param width  - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  @SuppressWarnings("unchecked")
  public void resize(int width, int height) {
    table.setBounds(0, 0, width, height * 0.1f);
    bar.setBounds(0, 0, width, height * 0.1f);
    buildingInfoTable.setBounds(0, height * 0.1f, width, height * 0.025f);

    // we must perform an unchecked type conversion here
    // this is acceptable as we know our table only contains instances of Actors
    for (Cell<Actor> cell : table.getCells()) {
      Image buildingImage = (Image) (cell.getActor());
      Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
      cell.width(
        height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
      ).height(
        height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
      );
    }

    buildingInfoLabel.setFontScale(height * 0.0015f);
  }

  /**
   * Called when the building menu needs to be redrawn with new values in the labels.
   */
  public void update() {
    if (GameState.gameOver) {
      buildingInfoLabel.setText("Game Over!");
    } else if (world.selectedBuilding == null) {
      buildingInfoLabel.setText("");
    }
  }

  public Image getLongboiImage(){
    return longboiImage;
  }

  public void reset() {
    buildingInfoLabel.setText("");
  }
}
