package com.couchbase.client.protocol.views;

public class InvalidViewException extends RuntimeException {

  private static final long serialVersionUID = 3816900873646688019L;

  public InvalidViewException() {
    super();
  }

  public InvalidViewException(String message) {
    super(message);
  }

  public InvalidViewException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidViewException(Throwable cause) {
    super(cause);
  }
}
