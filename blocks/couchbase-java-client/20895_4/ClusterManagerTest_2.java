
package com.couchbase.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import net.spy.memcached.compat.SpyObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ClusterManager extends SpyObject {

  private DefaultHttpClientConnection conn;
  private HttpContext context;
  private HttpHost host;
  private HttpRequestExecutor httpexecutor;
  private HttpProcessor httpproc;

  public ClusterManager(String hostname) {
    this(hostname, 8091);
  }

  public ClusterManager(String hostname, int port) {
    httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
      new RequestContent(), new RequestTargetHost(),
      new RequestConnControl(), new RequestUserAgent(),
      new RequestExpectContinue()});

    httpexecutor = new HttpRequestExecutor();

    context = new BasicHttpContext(null);
    host = new HttpHost(hostname, port);
    conn = new DefaultHttpClientConnection();

    context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
    context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);
  }

  private void connect() {
    try {
      if (!conn.isOpen()) {
        Socket socket = new Socket(host.getHostName(), host.getPort());
        conn.bind(socket, new SyncBasicHttpParams());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createDefaultBucket(BucketType type, int memorySizeMB,
      int replicas) throws HttpException{
    createBucket(type, "default", memorySizeMB, AuthType.NONE, replicas,
        11211, "");
  }

  public void createSaslBucket(BucketType type, String name,
      int memorySizeMB, int replicas, String authPassword)
    throws HttpException {
    createBucket(type, name, memorySizeMB, AuthType.SASL, replicas, 11212,
        authPassword);
  }

  public void createPortBucket(BucketType type, String name,
      int memorySizeMB, int replicas, int port) throws HttpException {
    createBucket(type, name, memorySizeMB, AuthType.NONE, replicas, port, "");
  }

  public void deleteBucket(String name) throws HttpException {
    String url = "/pools/default/buckets/" + name;
    BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("DELETE", url);

    checkError(200, sendRequest(request));
  }

  public List<String> listBuckets() throws HttpException {
    String url = "/pools/default/buckets/";
    BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("GET", url);

    HttpResult result = sendRequest(request);
    checkError(200, result);

    String json = result.getBody();
    List<String> names = new LinkedList<String>();
    if (json != null && !json.equals("")) {
      try {
        JSONArray base = new JSONArray(json);
        for (int i = 0; i < base.length(); i++) {
          JSONObject bucket = (JSONObject) base.get(i);
          if (bucket.has("name")) {
            names.add(bucket.getString("name"));
          }
        }
      } catch (JSONException e) {
        getLogger().error("Recieved bad response when getting bucket list");
      }
    }
    return names;
  }

  public void flushBucket(String name) throws HttpException {
    String url = "/pools/default/buckets/" + name + "/controller/doFlush";
    BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("POST", url);

    checkError(200, sendRequest(request));
  }

  private void createBucket(BucketType type, String name,
      int memorySizeMB, AuthType authType, int replicas, int port,
      String authpassword) throws HttpException {
    BasicHttpEntityEnclosingRequest request =
        new BasicHttpEntityEnclosingRequest("POST", "/pools/default/buckets");

    StringBuilder sb = new StringBuilder();
    sb.append("name=").append(name);
    sb.append("&ramQuotaMB=").append(memorySizeMB);
    sb.append("&authType=").append(authType.getAuthType());
    sb.append("&replicaNumber=").append(replicas);
    sb.append("&bucketType=").append(type.getBucketType());
    sb.append("&proxyPort=").append(port);
    if (authType == AuthType.SASL) {
      sb.append("&saslPassword=").append(authpassword);
    }

    try {
      request.setEntity(new StringEntity(sb.toString()));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    checkError(202, sendRequest(request));
  }

  private HttpResult sendRequest(HttpRequest request) throws HttpException {
    HttpParams params = new SyncBasicHttpParams();
    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setUserAgent(params, "Couchbase Java Client/1.1");
    HttpProtocolParams.setUseExpectContinue(params, true);

    request.addHeader("Authorization", "Basic "
        + Base64.encodeBase64String("Administrator:password".getBytes()));
    request.addHeader("Accept", "*/*");
    request.addHeader("Content-Type", "application/x-www-form-urlencoded");

    try {
      connect();
      httpexecutor.preProcess(request, httpproc, context);
      HttpResponse response = httpexecutor.execute(request, conn, context);
      httpexecutor.postProcess(response, httpproc, context);

      int code = response.getStatusLine().getStatusCode();
      String body = EntityUtils.toString(response.getEntity());
      String reason = parseError(body);
      String phrase = response.getStatusLine().getReasonPhrase();
      return new HttpResult(body, code, phrase, reason);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private String parseError(String json) {
    if (json != null && !json.equals("")) {
      try {
        JSONObject base = new JSONObject(json);
        if (base.has("errors")) {
          JSONObject errors = (JSONObject) base.get("errors");
          return errors.toString();
        }
      } catch (JSONException e) {
        return "Client error parsing error response";
      }
    }
    return "No reason given";
  }

  private void checkError(int expectedCode, HttpResult result)
    throws HttpException {
    if (result.getErrorCode() != expectedCode) {
      throw new HttpException("Http Error: " + result.getErrorCode()
          + " Reason: " + result.getErrorPhrase() + " Details: "
          + result.getReason());
    }
  }

  public boolean shutdown() {
    try {
      conn.close();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private final class HttpResult {
    private final String body;
    private final int errorCode;
    private final String errorPhrase;
    private final String errorReason;

    public HttpResult(String entity, int code, String phrase, String reason) {
      body = entity;
      errorCode = code;
      errorPhrase = phrase;
      errorReason = reason;
    }

    public String getBody() {
      return body;
    }

    public int getErrorCode() {
      return errorCode;
    }

    public String getErrorPhrase() {
      return errorPhrase;
    }

    public String getReason() {
      return errorReason;
    }
  }
}
