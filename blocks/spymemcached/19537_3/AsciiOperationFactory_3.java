
package net.spy.memcached.ops;

import net.spy.memcached.ObserveResponse;

public interface ObserveOperation extends KeyedOperation {
  interface Callback extends OperationCallback {
    void gotData(String key, long cas, ObserveResponse or);
  }
}
