
package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;


public class FlushOperationImpl extends HttpOperationImpl {

  public FlushOperationImpl(HttpRequest r, OperationCallback cb) {
    super(r, cb);
  }

  @Override
  public void handleResponse(HttpResponse response) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
