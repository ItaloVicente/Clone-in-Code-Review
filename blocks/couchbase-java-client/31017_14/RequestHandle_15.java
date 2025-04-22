
package com.couchbase.client.http;

import com.couchbase.client.ViewConnection;
import com.couchbase.client.protocol.views.HttpOperation;
import net.spy.memcached.compat.SpyObject;

import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HttpResponseCallback implements FutureCallback<HttpResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    HttpResponseCallback.class);

  private final HttpOperation op;

  private final ViewConnection vconn;

  private final HttpHost host;

  public HttpResponseCallback(final HttpOperation op,final ViewConnection vconn,
    final HttpHost host) {
    this.op = op;
    this.vconn = vconn;
    this.host = host;
  }

  @Override
  public void completed(final HttpResponse response) {
    try {
      response.setEntity(new BufferedHttpEntity(response.getEntity()));
    } catch(IOException ex) {
      throw new RuntimeException("Could not convert HttpEntity content.");
    }

    int statusCode = response.getStatusLine().getStatusCode();
    boolean shouldRetry = shouldRetry(statusCode, response);
    if (shouldRetry) {
      LOGGER.debug("Operation returned, but needs to be retried because "
        + "of: " + response.getStatusLine());
      retryOperation(op);
    } else {
      op.handleResponse(response);
    }
  }

  private void retryOperation(final HttpOperation op) {
    if(!op.isTimedOut() && !op.isCancelled()) {
      LOGGER.debug("Retrying HTTP operation from node ("
        + host.toHostString() + "), Request: "
        + op.getRequest().getRequestLine());
      vconn.addOp(op);
    }
  }

  @Override
  public void failed(final Exception e) {
    if (e instanceof SocketTimeoutException
      || e instanceof ConnectionClosedException) {
      retryOperation(op);
    } else {
      LOGGER.info("View Operation " + op.getRequest().getRequestLine()
        + " failed because of: ", e);
      op.cancel();
    }
  }

  @Override
  public void cancelled() {
    LOGGER.info("View Operation " + op.getRequest().getRequestLine()
      + " got cancelled.");
    op.cancel();
  }

  private static boolean shouldRetry(final int statusCode,
    final HttpResponse response) {
    switch(statusCode) {
      case 200:
        return false;
      case 404:
        return analyse404Response(response);
      case 500:
        return analyse500Response(response);
      case 300:
      case 301:
      case 302:
      case 303:
      case 307:
      case 401:
      case 408:
      case 409:
      case 412:
      case 416:
      case 417:
      case 501:
      case 502:
      case 503:
      case 504:
        return true;
      default:
        return false;
    }
  }

  private static boolean analyse404Response(HttpResponse response) {
    try {
      String body = EntityUtils.toString(response.getEntity());
      if(body.contains("not_found")
        && (body.contains("missing") || body.contains("deleted"))) {
        LOGGER.debug("Design Document not found, body: " + body);
        return false;
      }
    } catch(IOException ex) {
      return false;
    }
    return true;
  }

  private static boolean analyse500Response(HttpResponse response) {
    try {
      String body = EntityUtils.toString(response.getEntity());
      if(body.contains("error")
        && body.contains("{not_found, missing_named_view}")) {
        LOGGER.debug("Design Document not found, body: " + body);
        return false;
      }
    } catch(IOException ex) {
      return false;
    }
    return true;
  }

}
