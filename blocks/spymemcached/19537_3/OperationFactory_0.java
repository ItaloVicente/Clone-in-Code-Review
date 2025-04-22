
package net.spy.memcached;

public enum ObserveResponse {
  UNINITIALIZED((byte) 0xff),
  MODIFIED((byte) 0xfe),
  FOUND_PERSISTED((byte) 0x01),
  FOUND_NOT_PERSISTED((byte) 0x00),
  NOT_FOUND_PERSISTED((byte) 0x80),
  NOT_FOUND_NOT_PERSISTED((byte) 0x11);

  private final byte value;


  ObserveResponse(byte b) {
    value = b;
  }

}
