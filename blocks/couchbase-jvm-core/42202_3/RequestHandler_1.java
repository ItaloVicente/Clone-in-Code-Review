package com.couchbase.client.core;

public class BucketClosedException extends CouchbaseException {

  public BucketClosedException() {
  }

  public BucketClosedException(String message) {
    super(message);
  }

  public BucketClosedException(String message, Throwable cause) {
    super(message, cause);
  }
}

