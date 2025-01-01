package io.github.unisim;

/**
 * __NEW: WHOLE CLASS__ checked exception that ensures the username entered is of a valid format
 *
 * Valid usernames must be 32 characters or less in length.
 */
public class InvalidUsernameException extends Exception{

  public InvalidUsernameException(String username){
    super("the username " + username + " was invalid. It contains "
      + username.length() + " characters. The maximum allowed is 32");
  }

}
