package com.couchbase.client;

public class ObservedTimeoutException extends RuntimeException {

  static final long serialVersionUID = -8082610205299657671L;

  public ObservedTimeoutException() {
    super();
  }

  public ObservedTimeoutException(String message) {
    super(message);
  }

  public ObservedTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public ObservedTimeoutException(Throwable cause) {
    super(cause);
  }
}
