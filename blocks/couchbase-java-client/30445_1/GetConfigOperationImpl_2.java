package com.couchbase.client.vbucket.cccp;

import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationCallback;


public interface GetConfigOperation extends Operation {

  interface Callback extends OperationCallback {

    void gotData(byte[] data);
  }

}
