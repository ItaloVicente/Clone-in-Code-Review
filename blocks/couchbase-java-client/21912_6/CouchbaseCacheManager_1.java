
package com.couchbase.springframework;

public class CouchbaseCacheException extends RuntimeException {

  private static final long serialVersionUID = -3625467801237543267L;

  public CouchbaseCacheException() {
    super();
  }

  public CouchbaseCacheException(String message) {
    super(message);
  }

  public CouchbaseCacheException(String message, Throwable cause) {
    super(message, cause);
  }

  public CouchbaseCacheException(Throwable cause) {
    super(cause);
  }
}
