
package net.spy.memcached;

public enum ReplicaIndex {

  FIRST(0),

  SECOND(1),

  THIRD(2);

  private final int value;

  ReplicaIndex(int val) {
    value = val;
  }

  public int getValue() {
    return value;
  }

}
