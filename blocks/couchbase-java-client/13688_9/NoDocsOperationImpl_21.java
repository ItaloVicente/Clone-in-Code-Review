
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;

public interface NoDocsOperation {

  interface NoDocsCallback extends OperationCallback {
    void gotData(ViewResponse response);
  }
}
