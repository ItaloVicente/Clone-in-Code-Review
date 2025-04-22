
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;

public interface ViewOperation {

  interface ViewCallback extends OperationCallback {
    void gotData(ViewResponse response);
  }
}
