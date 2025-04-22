
package com.couchbase.client.protocol.views;

import java.util.List;

import net.spy.memcached.ops.OperationCallback;

public interface ViewsFetcherOperation {

  interface ViewsFetcherCallback extends OperationCallback {
    void gotData(List<View> views);
  }
}
