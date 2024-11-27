package io.github.unisim;

/**
 * Contains global settings for the game such as volume.
 */
public class Settings {
  public static float MIN_VOLUME = 0f;
  public static float MAX_VOLUME = 1f;
  private float volume = 1.0f;

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    // Constrain volume between limits
    this.volume = Math.max(Math.min(volume, MAX_VOLUME), MIN_VOLUME);
  }
}
