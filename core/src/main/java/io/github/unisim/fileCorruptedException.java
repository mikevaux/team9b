package io.github.unisim;

/**
 * __NEW: WHOLE CLASS__ checked exception that ensures the file is in the right format.
 * If incorrect format, only the Home button is displayed
 */
public class fileCorruptedException extends Exception{

  public fileCorruptedException(){
    super("file format is not as expected");
  }

}
