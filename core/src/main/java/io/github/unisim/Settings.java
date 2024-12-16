package io.github.unisim;

/**
 * Contains global settings for the game such as volume.
 */
public class Settings {
  /**
   * __NEW: CONSTANT__ The minimum applicable volume.
   */
  public static float MIN_VOLUME = 0f;
  /**
   * __NEW: CONSTANT__ The maximum applicable volume.
   */
  public static float MAX_VOLUME = 1f;
  /**
   * The current volume.
   */
  private float volume = 1.0f;

  public float getVolume() {
    return volume;
  }

  /**
   * __NEW: METHOD__ Sets the volume after constraining between limits.
   *
   * @param volume the volume to set
   */
  public void setVolume(float volume) {
    this.volume = Math.max(Math.min(volume, MAX_VOLUME), MIN_VOLUME);
  }
}
