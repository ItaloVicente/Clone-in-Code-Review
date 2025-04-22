
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;


public interface DesignDocOperation {

  interface DesignDocCallback extends OperationCallback {
    void gotData(String response);
  }
}
