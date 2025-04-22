
package com.couchbase.client.protocol.views;

public enum OnError {
  STOP {
    public String toString() {
      return "stop";
    }
  },

  CONTINUE {
    public String toString() {
      return "continue";
    }
  }
}
