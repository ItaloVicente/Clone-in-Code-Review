
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;

public interface ViewFetcherOperation {

  public interface ViewFetcherCallback extends OperationCallback {
    void gotData(View view);
  }
}
