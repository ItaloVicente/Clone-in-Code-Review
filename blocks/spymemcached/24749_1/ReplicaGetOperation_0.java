
package net.spy.memcached;

public enum ReplicaIndex {

  FIRST(1),

  SECOND(2),

  THIRD(3),

  FOURTH(4);

  private final int value;

  ReplicaIndex(int val) {
    value = val;
  }

  public int getValue() {
    return value;
  }
  
}
