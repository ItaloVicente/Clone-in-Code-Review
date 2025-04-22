
package com.couchbase.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.naming.ConfigurationException;

import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.AuthThreadMonitor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.transcoders.TranscodeService;
import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.ConfigurationProviderHTTP;
import com.couchbase.client.vbucket.Reconfigurable;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.BroadcastOpFactory;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.OperationFactory;
import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.Config;

public class TapConnectionProvider extends SpyObject implements
    ConnectionObserver, Reconfigurable {

  private volatile boolean shuttingDown = false;

  private final CouchbaseConnection conn;

  private final OperationFactory opFact;

  private final TranscodeService tcService;

  private final AuthDescriptor authDescriptor;

  private final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

  private ConfigurationProvider configurationProvider;

  public TapConnectionProvider(InetSocketAddress... ia) throws IOException {
    this(new BinaryConnectionFactory(), Arrays.asList(ia));
  }

  public TapConnectionProvider(List<InetSocketAddress> addrs)
    throws IOException {
    this(new BinaryConnectionFactory(), addrs);
  }

  private TapConnectionProvider(ConnectionFactory cf,
      List<InetSocketAddress> addrs) throws IOException {
    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException(
          "You must have at least one server to connect to");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    tcService = new TranscodeService(cf.isDaemon());
    cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    conn = (CouchbaseConnection)cf.createConnection(addrs);
    assert conn != null : "Connection factory failed to make a connection";
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
    }
  }

  public TapConnectionProvider(final List<URI> baseList,
      final String bucketName, final String usr, final String pwd)
    throws IOException, ConfigurationException {
    for (URI bu : baseList) {
      if (!bu.isAbsolute()) {
        throw new IllegalArgumentException("The base URI must be absolute");
      }
    }

    this.configurationProvider =
        new ConfigurationProviderHTTP(baseList, usr, pwd);
    Bucket bucket =
        this.configurationProvider.getBucketConfiguration(bucketName);
    Config config = bucket.getConfig();
    ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
    if (config.getConfigType() == ConfigType.MEMBASE) {
      cfb.setFailureMode(FailureMode.Retry)
          .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
          .setHashAlg(config.getHashAlgorithm())
          .setLocatorType(ConnectionFactoryBuilder.Locator.VBUCKET)
          .setVBucketConfig(bucket.getConfig());
    } else if (config.getConfigType() == ConfigType.MEMCACHE) {
      cfb.setFailureMode(FailureMode.Redistribute)
          .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
          .setHashAlg(config.getHashAlgorithm())
          .setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT)
          .setShouldOptimize(false);
    } else {
      throw new ConfigurationException(
          "Bucket type not supported or JSON response unexpected");
    }*/
    if (!this.configurationProvider.getAnonymousAuthBucket().equals(bucketName)
        && usr != null) {
      AuthDescriptor ad =
          new AuthDescriptor(new String[] { "PLAIN" },
              new PlainCallbackHandler(usr, pwd));
      cfb.setAuthDescriptor(ad);
    }
    ConnectionFactory cf = cfb.build();
    List<InetSocketAddress> addrs =
        AddrUtil.getAddresses(bucket.getConfig().getServers());
    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException(
          "You must have at least one server to connect to");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    tcService = new TranscodeService(cf.isDaemon());
    cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    conn = (CouchbaseConnection)cf.createConnection(addrs);
    assert conn != null : "Connection factory failed to make a connection";
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
    }
    this.configurationProvider.subscribe(bucketName, this);
  }

  Operation addOp(final Operation op) {
    conn.checkState();
    conn.addOperation("", op);
    return op;
  }

  public boolean addObserver(ConnectionObserver obs) {
    boolean rv = conn.addObserver(obs);
    if (rv) {
      for (MemcachedNode node : conn.getLocator().getAll()) {
        if (node.isActive()) {
          obs.connectionEstablished(node.getSocketAddress(), -1);
        }
      }
    }
    return rv;
  }

  public boolean removeObserver(ConnectionObserver obs) {
    return conn.removeObserver(obs);
  }

  public void connectionEstablished(SocketAddress sa, int reconnectCount) {
    if (authDescriptor != null) {
      if (authDescriptor.authThresholdReached()) {
        this.shutdown();
      } else {
        authMonitor.authConnection(conn, opFact, authDescriptor, findNode(sa));
      }
    }
  }

  private MemcachedNode findNode(SocketAddress sa) {
    MemcachedNode node = null;
    for (MemcachedNode n : conn.getLocator().getAll()) {
      if (n.getSocketAddress().equals(sa)) {
        node = n;
      }
    }
    assert node != null : "Couldn't find node connected to " + sa;
    return node;
  }

  public void connectionLost(SocketAddress sa) {
  }

  public void reconfigure(Bucket bucket) {
    this.conn.reconfigure(bucket);
  }

  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public boolean shutdown(long timeout, TimeUnit unit) {
    if (shuttingDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }
    shuttingDown = true;
    String baseName = conn.getName();
    conn.setName(baseName + " - SHUTTING DOWN");
    boolean rv = false;
    try {
      if (timeout > 0) {
        conn.setName(baseName + " - SHUTTING DOWN (waiting)");
        rv = waitForQueues(timeout, unit);
      }
    } finally {
      try {
        conn.setName(baseName + " - SHUTTING DOWN (telling client)");
        conn.shutdown();
        conn.setName(baseName + " - SHUTTING DOWN (informed client)");
        tcService.shutdown();
        if (this.configurationProvider != null) {
          this.configurationProvider.shutdown();
        }
      } catch (IOException e) {
        getLogger().warn("exception while shutting down", e);
      }
    }
    return rv;
  }

  public boolean waitForQueues(long timeout, TimeUnit unit) {
    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        return opFact.noop(new OperationCallback() {
          public void complete() {
            latch.countDown();
          }

          public void receivedStatus(OperationStatus s) {
          }
        });
      }
    }, conn.getLocator().getAll(), false);
    try {
      return blatch.await(timeout, unit);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for queues", e);
    }
  }

  CountDownLatch broadcastOp(final BroadcastOpFactory of) {
    return broadcastOp(of, conn.getLocator().getAll(), true);
  }

  CountDownLatch broadcastOp(final BroadcastOpFactory of,
      Collection<MemcachedNode> nodes) {
    return broadcastOp(of, nodes, true);
  }

  private CountDownLatch broadcastOp(BroadcastOpFactory of,
      Collection<MemcachedNode> nodes, boolean checkShuttingDown) {
    if (checkShuttingDown && shuttingDown) {
      throw new IllegalStateException("Shutting down");
    }
    return conn.broadcastOperation(of, nodes);
  }

  public OperationFactory getOpFactory() {
    return opFact;
  }
}
