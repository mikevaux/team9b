package io.github.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.unisim.Bank;
import io.github.unisim.GameState;
import io.github.unisim.Main;
import io.github.unisim.FileCorruptedException;

import java.util.ArrayList;

/**
 * __NEW: WHOLE CLASS__ leaderboard screen that displays the users score
 * as well as the leaderboard of the top 5 scores
 */
public class LeaderboardScreen implements Screen {
  private Stage stage;
  private Table table;
  private Label titleLabel;
  private Label userScoreLabel;
  private Table leaderboard;
  private TextButton homeButton;
  private InputMultiplexer inputMultiplexer = new InputMultiplexer();
  private boolean changed = false;

  /**
   * Create a new Leaderboard screen and draw the initial UI layout.
   */
  public LeaderboardScreen(String username, int satisfaction){
    stage = new Stage();
    table = new Table();
    Skin skin = GameState.getInstance().getDefaultSkin();

    // Error message label to be displayed if the file is corrupted
    Label errorLabel = new Label("An error occurred loading the leaderboard :/", skin, "window");

    // Title label
    titleLabel = new Label("Leaderboard", skin, "window");

    // User score label
    userScoreLabel = new Label("Congratulations " + username
      + " your score was: " + satisfaction + "!", skin);
    userScoreLabel.setColor(new Color(0.9f, 0.9f, 0.9f, 1.0f));

    // Add title and user score labels to table
    table.add(titleLabel).center().padBottom(30);
    table.row();
    table.add(userScoreLabel).center().padBottom(30);
    table.row();

    try {
      loadLeaderboard(username, satisfaction, skin);
    } catch (FileCorruptedException e) {
      System.out.println(e.getMessage());
      table.add(errorLabel).padBottom(40);
      table.row();
    }

    // Home button
    homeButton = new TextButton("Main Menu", skin);
    homeButton.setPosition(150, 80);
    homeButton.setSize(200, 60);
    homeButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        // Go back to the start menu, and reset the game instance
        Main.getInstance().setScreen(new StartMenuScreen());
        GameState.wipeInstance();
        Bank.wipeInstance();
      }
    });
    table.add(homeButton).center().width(250).height(67);
    table.pad(100, 100, 100, 100);
    table.setFillParent(true);
    table.center().center();
    stage.addActor(table);

    inputMultiplexer.addProcessor(GameState.getInstance().getFullscreenInputProcessor());
    inputMultiplexer.addProcessor(stage);
  }

  private void loadLeaderboard(String username, int satisfaction, Skin skin) throws FileCorruptedException {
    // Leaderboard table
    Label usernameHeader = new Label("Username", skin, "window");
    Label scoreHeader = new Label("Satisfaction", skin, "window");
    leaderboard = new Table();
    leaderboard.add(usernameHeader).padBottom(10).padRight(5);
    leaderboard.add(scoreHeader).padBottom(10).padLeft(5);
    leaderboard.row();

    try {
      FileHandle leaderboardFile = Gdx.files.internal("textFiles/leaderboard.txt");
      String[] splitArray = leaderboardFile.readString().split(",\n");
      ArrayList<String> usernames = new ArrayList<>();
      ArrayList<String> scores = new ArrayList<>();
      for(int i = 0; i <= 4; i ++){
        String usernameToAdd = splitArray[i].split(",")[0];
        String scoreToAdd = splitArray[i].split(",")[1];
        usernames.add(usernameToAdd);
        scores.add(scoreToAdd);
      }
      updateLeaderboard(username, usernames, satisfaction, scores);

      for (int i = 0; i <= (usernames.size() - 1); i++) {
        Label currentUsernameLabel = new Label(usernames.get(i), skin);
        Label currentScoreLabel = new Label(scores.get(i), skin);
        leaderboard.add(currentUsernameLabel).padBottom(2);
        leaderboard.add(currentScoreLabel).padBottom(2);
        leaderboard.row();
      }

      // Add UI elements to stage
      table.add(leaderboard).center().padBottom(30);
      table.row();

      // modify the file to reflect the new leaderboard if changed
      if (changed){
        FileHandle file = Gdx.files.local("textFiles/leaderboard.txt");
        String newScores = "";
        for (int i = 0; i < usernames.size(); i++) {
          newScores += usernames.get(i) + "," + scores.get(i) + ",\n";
        }
        file.writeString(newScores, false);

      }
    }
    catch(Exception e) {
      throw new FileCorruptedException();
    }
  }

  private void updateLeaderboard(String username, ArrayList<String> usernames, int satisfaction, ArrayList<String> scores){

    String tempUsername = null;
    String tempScore = null;
    int i = usernames.size() - 1;
    while (i >= 0 && satisfaction >= Integer.valueOf(scores.get(i))){
      changed = true;
      tempUsername = usernames.get(i);
      tempScore = scores.get(i);
      usernames.set(i, username);
      scores.set(i, String.valueOf(satisfaction));
      if (i != usernames.size()-1){
        usernames.set(i+1, tempUsername);
        scores.set(i+1, tempScore);
      }
      i--;
    }
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void render(float delta) {
    // Clear the screen
    ScreenUtils.clear(GameState.getInstance().getColourSecondary());

    // Draw the stage containing the volume slider and buttons
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }

}
