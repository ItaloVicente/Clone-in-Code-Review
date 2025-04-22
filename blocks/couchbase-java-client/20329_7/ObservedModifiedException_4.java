package com.couchbase.client;

public class ObservedException extends RuntimeException {

  static final long serialVersionUID = -815352665628228664L;

  public ObservedException() {
    super();
  }

  public ObservedException(String message) {
    super(message);
  }

  public ObservedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ObservedException(Throwable cause) {
    super(cause);
  }
}
