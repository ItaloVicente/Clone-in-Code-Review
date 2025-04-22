package com.couchbase.client.protocol.views;

import net.spy.memcached.ops.OperationCallback;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public class DesignDocOperationImpl extends HttpOperationImpl implements DesignDocOperation {

  public DesignDocOperationImpl(HttpRequest request, OperationCallback operationCallback) {
    super(request, operationCallback);
  }

  @Override
  public void handleResponse(HttpResponse response) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}


