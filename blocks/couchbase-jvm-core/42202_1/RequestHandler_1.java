package com.couchbase.client.core;

public class BucketClosedException extends RuntimeException {

  public BucketClosedException() {
  }

  public BucketClosedException(String message) {
    super(message);
  }

  public BucketClosedException(String message, Throwable cause) {
    super(message, cause);
  }
}

