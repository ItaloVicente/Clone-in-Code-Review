
package net.spy.memcached;

public enum ObserveResponse {
  UNINITIALIZED,
  MODIFIED,
  FOUND_PERSISTED,
  FOUND_NOT_PERSISTED,
  NOT_FOUND_PERSISTED,
  NOT_FOUND_NOT_PERSISTED
}
