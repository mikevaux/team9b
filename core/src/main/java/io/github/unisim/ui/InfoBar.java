package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;

/**
 * Create a Title bar with basic info.
 */
public class InfoBar {
  private ShapeActor bar;
  private Table infoTable = new Table();
  private Table titleTable = new Table();
  private Table buildingCountersTable = new Table();
  private Label[] buildingCounterLabels = new Label[4];
  private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
  private Label satisfactionLabel = new Label("0", skin, "window");
  private Label titleLabel = new Label("UniSim", skin, "window");
  private Label timerLabel;
  private Texture pauseTexture = new Texture("ui/pause.png");
  private Texture playTexture = new Texture("ui/play.png");
  private Image pauseImage = new Image(pauseTexture);
  private Image playImage = new Image(playTexture);
  private Timer timer;
  private Cell<Label> timerLabelCell;
  private Cell<Label> satisfactionLabelCell;
  private Cell<Image> pauseButtonCell;
  private Cell<Table> buildingCountersTableCell;
  private Cell[] buildingCounterCells;
  private World world;
  /**
   * Create a new infoBar and draws its' components onto the provided stage.

   * @param stage - The stage on which to draw the InfoBar.
   */
  public InfoBar(Stage stage, Timer timer, World world) {
    this.timer = timer;
    this.world = world;
    buildingCounterCells = new Cell[4];

    // Building counter table
    for (int i = 0; i < 4; i++) {
      buildingCounterLabels[i] = new Label("", skin);
    }
    buildingCounterCells[0] = buildingCountersTable.add(buildingCounterLabels[0]).left().padRight(16);
    buildingCounterCells[1] = buildingCountersTable.add(buildingCounterLabels[1]).left();
    buildingCountersTable.row().padTop(4);
    buildingCounterCells[2] = buildingCountersTable.add(buildingCounterLabels[2]).left().padRight(16);
    buildingCounterCells[3] = buildingCountersTable.add(buildingCounterLabels[3]).left();

    // Info Table
    timerLabel = new Label(timer.displayProgression(), skin, "window");
    infoTable.pad(24);
    pauseButtonCell = infoTable.add(playImage).left().size(24).padRight(24);
    timerLabelCell = infoTable.add(timerLabel).left().padRight(24);
    satisfactionLabelCell = infoTable.add(satisfactionLabel).right().padLeft(24);
    buildingCountersTableCell = infoTable.add(buildingCountersTable).expandX().right();

    // Pause button
    pauseImage.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        GameState.getInstance().setPaused(true);
        pauseButtonCell.setActor(playImage);
      }
    });

    // Play button
    playImage.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        GameState.getInstance().setPaused(false);
        pauseButtonCell.setActor(pauseImage);
      }
    });

    titleTable.add(titleLabel).expandX().align(Align.center);

    bar = new ShapeActor(GameState.getInstance().getColourPrimary());
    stage.addActor(bar);
    stage.addActor(infoTable);
    stage.addActor(titleTable);
  }

  /**
   * Called when the UI needs to be updated, usually on every frame.
   */
  public void update() {
    timerLabel.setText(timer.displayProgression());
    buildingCounterLabels[0].setText("Recreation: "
        + Integer.toString(world.getBuildingCount(BuildingType.RECREATION)));
    buildingCounterLabels[1].setText("Learning: "
        + Integer.toString(world.getBuildingCount(BuildingType.LEARNING)));
    buildingCounterLabels[2].setText("Eating: "
        + Integer.toString(world.getBuildingCount(BuildingType.EATING)));
    buildingCounterLabels[3].setText("Sleeping: "
        + Integer.toString(world.getBuildingCount(BuildingType.SLEEPING)));
    pauseButtonCell.setActor(GameState.getInstance().isPaused() ? playImage : pauseImage);
  }

  /**
   * Update the bounds of the background & table actors to fit the new size of the screen.

   * @param width - The new width of the screen in pixels.
   * @param height - The enw height of the screen in pixels.
   */
  public void resize(int width, int height) {
    bar.setBounds(0, height * 0.95f, width, height * 0.05f);
    infoTable.setBounds(0, height * 0.95f, width, height * 0.05f);
    titleTable.setBounds(0, height * 0.95f, width, height * 0.05f);
  }

  public void updateSatisfactionLabel(String satisfaction){
    satisfactionLabel.setText(satisfaction);
  }
}
