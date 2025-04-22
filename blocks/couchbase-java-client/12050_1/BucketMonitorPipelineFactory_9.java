
package com.couchbase.client.vbucket;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.ParseException;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.ConfigurationParser;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;

public class BucketMonitor extends Observable {

  private final URI cometStreamURI;
  private Bucket bucket;
  private final String httpUser;
  private final String httpPass;
  private final ChannelFactory factory;
  private Channel channel;
  private final String host;
  private final int port;
  private ConfigurationParser configParser;
  private BucketUpdateResponseHandler handler;
  private final HttpMessageHeaders headers;
  public static final String CLIENT_SPEC_VER = "1.0";

  public BucketMonitor(URI cometStreamURI, String bucketname, String username,
      String password, ConfigurationParser configParser) {
    super();
    if (cometStreamURI == null) {
      throw new IllegalArgumentException("cometStreamURI cannot be NULL");
    }
    String scheme = cometStreamURI.getScheme() == null ? "http"
        : cometStreamURI.getScheme();
    if (!scheme.equals("http")) {
      throw new UnsupportedOperationException("Only http is supported.");
    }

    this.cometStreamURI = cometStreamURI;
    this.httpUser = username;
    this.httpPass = password;
    this.configParser = configParser;
    this.host = cometStreamURI.getHost();
    this.port = cometStreamURI.getPort() == -1 ? 80 : cometStreamURI.getPort();
    factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool());
    this.headers = new HttpMessageHeaders();
  }

  private static final class HttpMessageHeaders {

    private final Method m;

    private HttpMessageHeaders() {
      this(getHttpMessageHeaderStrategy());
    }

    private HttpMessageHeaders(final Method m) {
      this.m = m;
    }

    private static Method getHttpMessageHeaderStrategy() {
      try {
        return HttpRequest.class.getMethod("setHeader", String.class,
          Object.class);
      } catch (final SecurityException e) {
        throw new RuntimeException(
          "Cannot check method due to security restrictions.", e);
      } catch (final NoSuchMethodException e) {
        try {
          return HttpRequest.class.getMethod("setHeader", String.class,
            String.class);
        } catch (final Exception e1) {
          throw new RuntimeException(
            "No suitable setHeader method found on netty HttpRequest, the "
            + "signature seems to have changed.", e1);
        }
      }
    }

    void setHeader(HttpRequest obj, String name, String value) {
      try {
        m.invoke(obj, name, value);
      } catch (final Exception e) {
        throw new RuntimeException("Could not invoke method " + m
          + " with args '" + name + "' and '" + value + "'.", e);
      }
    }

  }

  public void startMonitor() {
    if (channel != null) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
          "Bucket monitor is already started.");
      return;
    }
    createChannel();
    this.handler = channel.getPipeline().get(BucketUpdateResponseHandler.class);
    handler.setBucketMonitor(this);
    HttpRequest request = prepareRequest(cometStreamURI, host);
    channel.write(request);
    try {
      String response = this.handler.getLastResponse();
      logFiner("Getting server list returns this last chunked response:\n"
          + response);
      Bucket bucketToMonitor = this.configParser.parseBucket(response);
      setBucket(bucketToMonitor);
    } catch (ParseException ex) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
        "Invalid client configuration received from server. Staying with "
        + "existing configuration.", ex);
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINE,
        "Invalid client configuration received:\n" + handler.getLastResponse()
        + "\n");
    }
  }

  protected void createChannel() {
    ClientBootstrap bootstrap = new ClientBootstrap(factory);

    bootstrap.setPipelineFactory(new BucketMonitorPipelineFactory());

    ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

    channel = future.awaitUninterruptibly().getChannel();
    if (!future.isSuccess()) {
      bootstrap.releaseExternalResources();
      throw new ConnectionException("Could not connect to any pool member.");
    }
    assert (channel != null);
  }

  protected HttpRequest prepareRequest(URI uri, String h) {
    HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
      HttpMethod.GET, uri.toASCIIString());
    headers.setHeader(request, HttpHeaders.Names.HOST, h);
    if (getHttpUser() != null) {
      String basicAuthHeader;
      try {
        basicAuthHeader =
          ConfigurationProviderHTTP.buildAuthHeader(getHttpUser(),
            getHttpPass());
        headers.setHeader(request, HttpHeaders.Names.AUTHORIZATION,
          basicAuthHeader);
      } catch (UnsupportedEncodingException ex) {
        throw new RuntimeException("Could not encode specified credentials"
            + " for HTTP request.", ex);
      }
    }
    headers.setHeader(request, HttpHeaders.Names.CONNECTION,
      HttpHeaders.Values.CLOSE);  // No keep-alives for this
    headers.setHeader(request, HttpHeaders.Names.CACHE_CONTROL,
      HttpHeaders.Values.NO_CACHE);
    headers.setHeader(request, HttpHeaders.Names.ACCEPT, "application/json");
    headers.setHeader(request, HttpHeaders.Names.USER_AGENT,
      "spymemcached vbucket client");
    headers.setHeader(request,
      "X-memcachekv-Store-Client-Specification-Version", CLIENT_SPEC_VER);
    return request;
  }

  private void setBucket(Bucket newBucket) {
    if (this.bucket == null || !this.bucket.equals(newBucket)) {
      this.bucket = newBucket;
      setChanged();
      notifyObservers(this.bucket);
    }
  }

  public String getHttpUser() {
    return httpUser;
  }

  public String getHttpPass() {
    return httpPass;
  }

  private void logFiner(String msg) {
    Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINER, msg);
  }

  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public void shutdown(long timeout, TimeUnit unit) {
    deleteObservers();
    if (channel != null) {
      channel.close().awaitUninterruptibly(timeout, unit);
    }
    factory.releaseExternalResources();
  }

  protected void invalidate() {
    try {
      String response = handler.getLastResponse();
      Bucket updatedBucket = this.configParser.parseBucket(response);
      setBucket(updatedBucket);
    } catch (ParseException e) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.SEVERE,
          "Invalid client configuration received from server. Staying with "
          +  "existing configuration.", e);
    }
  }

  public void setConfigParser(ConfigurationParser newConfigParser) {
    this.configParser = newConfigParser;
  }
}
