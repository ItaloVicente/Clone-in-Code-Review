package com.couchbase.client;

public class ObservedModifiedException extends RuntimeException {

  static final long serialVersionUID = -6092571632069323220L;

  public ObservedModifiedException() {
    super();
  }

  public ObservedModifiedException(String message) {
    super(message);
  }

  public ObservedModifiedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ObservedModifiedException(Throwable cause) {
    super(cause);
  }
}

