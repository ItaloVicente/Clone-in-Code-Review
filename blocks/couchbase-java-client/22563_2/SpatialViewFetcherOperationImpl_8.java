
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;

public interface SpatialViewFetcherOperation {

  public interface ViewFetcherCallback extends OperationCallback {
    void gotData(SpatialView view);
  }
}
