
package com.couchbase.client;

import net.spy.memcached.ops.OperationCallback;

public interface TestOperation {
  interface TestCallback extends OperationCallback {
    void getData(String response);
  }
}
