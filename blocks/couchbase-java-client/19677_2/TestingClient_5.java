
package com.couchbase.client;

import com.couchbase.client.protocol.views.HttpOperationImpl;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.spy.memcached.ops.OperationStatus;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public class TestOperationPutImpl extends HttpOperationImpl implements
    TestOperation {

  public TestOperationPutImpl(HttpRequest r, TestCallback testCallback) {
    super(r, testCallback);
  }

  @Override
  public void handleResponse(HttpResponse response) {
    StringBuilder json = new StringBuilder("");  // workaround for null returns
    json.append(getEntityString(response));
    int errorcode = response.getStatusLine().getStatusCode();
    InputStream bi;
    StringBuffer responseContent = new StringBuffer("");
    try {
      bi = response.getEntity().getContent();
      byte[] buffer = new byte[bi.available() ];
      int bytesRead = bi.read(buffer);
      responseContent.append(new String(buffer));
    } catch (IOException ex) {
      Logger.getLogger(TestOperationImpl.class.getName()).log(
              Level.SEVERE, "Could not read test response.", ex);
    } catch (IllegalStateException ex) {
      Logger.getLogger(TestOperationImpl.class.getName()).log(
              Level.SEVERE, null, ex);
    }



    if (errorcode == HttpURLConnection.HTTP_CREATED) {
      ((TestCallback) callback).getData(json.toString());
      callback.receivedStatus(new OperationStatus(true, "OK"));
    } else {
      callback.receivedStatus(new OperationStatus(false,
          Integer.toString(errorcode) + ": " + responseContent));
    }
    callback.complete();
  }

}
