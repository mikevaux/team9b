package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.unisim.Bank;
import io.github.unisim.GameState;
import io.github.unisim.building.*;
import io.github.unisim.messages.MessageHandler;
import io.github.unisim.world.World;

import java.util.ArrayList;

/**
 * Menu used to place buildings in the world by clicking and dragging them
 * from the list onto the map.
 */
@SuppressWarnings({"MemberName", "AbbreviationAsWordInName"})
public class BuildingMenu {
  private Stage stage;
  private World world;
  private final MessageHandler messageHandler;
  private final ShapeActor bar = new ShapeActor(GameState.getInstance().getColourSecondary());
  private Table table;
  private Skin skin;
  private Table balanceTable;
  private Label balanceLabel;
  private Table buildingInfoTable;
  private Label buildingInfoLabel;
  private ArrayList<Widget> longBoiWidgets;

  /**
   * Create a Building Menu and attach its actors and components to the provided stage.
   * Also handles drawing buildings and their flipped variants
   *
   * @param stage - The stage on which to draw the menu
   * @param world - The instance of World that the game is played in
   */
  public BuildingMenu(Stage stage, World world, MessageHandler messageHandler) {
    this.stage = stage;
    this.world = world;
    this.messageHandler = messageHandler;
    skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    table = new Table();
    longBoiWidgets = new ArrayList<>();

    ArrayList<Building> buildings = new ArrayList<>();
    buildings.add(new EatingBuilding());
    buildings.add(new LearningBuilding());
    buildings.add(new BasketballCourt());
    buildings.add(new Stadium());
    buildings.add(new SleepingBuilding());
    buildings.add(new LongBoiStatue());

    table.row();
    // Add buildings to the table
    for (Building building : buildings) {
      Image image = new Image(building.getTexture());
      table.add(image);
      addBuildingListeners(building, image);
      handleEventBuilding(building, image);
    }

    table.row();

    for (Building building : buildings) {
      Label label = new Label(building.displayCost(), skin);
      table.add(label).pad(12);
      handleEventBuilding(building, label);
    }

    buildingInfoTable = new Table();
    buildingInfoLabel = new Label("", skin);
    buildingInfoTable.add(buildingInfoLabel).expandX();

    balanceTable = new Table();
    // Manually set the size so that the positioning (in `resize`) can position properly
    balanceTable.setSize(148, 64);
    balanceTable.left();
    balanceTable.row().left();
    balanceTable.add(new Label("Balance", skin, "window")).padBottom(8);
    balanceTable.row().left();
    balanceLabel = new Label(Bank.getInstance().displayBalance(), skin);
    balanceTable.add(balanceLabel);

    stage.addActor(bar);
    stage.addActor(table);
    stage.addActor(balanceTable);
    stage.addActor(buildingInfoTable);
  }

  private void handleEventBuilding(Building building, Widget widget) {
    // Event buildings are only available during the relevant event
    if (building instanceof EventBuilding) {
      widget.setVisible(false);

      // Cache the LongBoi widget for conditional showing / hiding
      if (building instanceof LongBoiStatue) {
        longBoiWidgets.add(widget);
      }
    }
  }

  private void addBuildingListeners(Building building, Image image) {
    image.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        // Don't allow building if the funds are insufficient
        if (Bank.getInstance().getBalance() < building.getCost()) {
          messageHandler.showMessage(
            "Insufficient Funds",
            "Your bank balance is too low to build a " + building.getName() + " at the moment."
          );
          return;
        }

        try {
          // Use reflection to create a new instance of the respective class
          world.selectedBuilding = building.getClass().getConstructor().newInstance();
          // Update the label to provide some helpful pointers to the player
          buildingInfoLabel.setText(world.selectedBuilding.getName() + " - Press 'R' to rotate, 'Esc' to cancel");
          System.out.println(buildingInfoLabel.getText());
        } catch (NoSuchMethodException e) {
          System.out.println("Error: could not find public nullary constructor for " + building.getClass().getName());
        } catch (Exception e) {
          System.out.println("Something went wrong when creating a new building");
        }
      }
    });
  }

  /**
   * Called when the window is resized, scales the building menu images with the window size.
   *
   * @param width  - The new width of the window in pixels
   * @param height - The new height of the window in pixels
   */
  @SuppressWarnings("unchecked")
  public void resize(int width, int height) {
    // Because everything in this game layout has been built to-the-pixel rather than dynamically, we need to manually
    // add in space for the newly added text. This would be far better to rework so that actors are sized dynamically,
    // but we don't have time for that at the moment, so this textSpacing is a workaround for the time being.
    float textSpacing = 32;
    float barHeight = height * 0.1f + textSpacing;
    table.setBounds(0, 0, width, barHeight);
    bar.setBounds(0, 0, width, barHeight);

    // we must perform an unchecked type conversion here
    // this is acceptable as we know our table only contains instances of Actors
    for (Cell<Actor> cell : table.getCells()) {
      // This logic was based on the assumption that all actors in this table would be Images.
      // This is no longer the case unfortunately, and the logic is difficult to de-tangle well, so this
      // is just a workaround for now so we don't try and make an illegal cast.
      if (!(cell.getActor() instanceof Image)) {
        continue;
      }

      Image buildingImage = (Image) (cell.getActor());
      Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
      cell.width(
        height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
      ).height(
        height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
      );
    }

    buildingInfoTable.setBounds(0, barHeight, width, 24);
    balanceTable.setPosition(32, barHeight/2 - balanceTable.getHeight()/2);
  }

  /**
   * Called when the building menu needs to be redrawn with new values in the labels.
   */
  public void update() {
    if (GameState.gameOver) {
      buildingInfoLabel.setText("Game Over!");
      return;
    }

    if (world.selectedBuilding == null) {
      buildingInfoLabel.setText("");
    }

    balanceLabel.setText(Bank.getInstance().displayBalance());
  }

  /**
   * Sets the visibility of each Long Boi widget, which is conditionally shown.
   *
   * @param visible true if Long Boi should be shown
   */
  public void setLongBoiVisibility(boolean visible) {
    for (Widget widget : longBoiWidgets) {
      widget.setVisible(visible);
    }
  }

  public void reset() {
    buildingInfoLabel.setText("");
  }
}
