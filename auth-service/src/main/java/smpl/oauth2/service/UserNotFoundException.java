package smpl.oauth2.service;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {}

  public UserNotFoundException(String uuid) {
    super("uuid: " + uuid);
  }
}
