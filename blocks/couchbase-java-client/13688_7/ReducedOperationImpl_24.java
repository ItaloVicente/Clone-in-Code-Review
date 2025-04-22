
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;

public interface ReducedOperation {

  interface ReducedCallback extends OperationCallback {
    void gotData(ViewResponse response);
  }
}
