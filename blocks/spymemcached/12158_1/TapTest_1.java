
package net.spy.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.AuthThreadMonitor;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.transcoders.TranscodeService;

public class TapConnectionProvider extends SpyObject implements
    ConnectionObserver {

  protected volatile boolean shuttingDown = false;

  protected final MemcachedConnection conn;

  protected final OperationFactory opFact;

  protected final TranscodeService tcService;

  protected final AuthDescriptor authDescriptor;

  protected final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

  public TapConnectionProvider(InetSocketAddress... ia) throws IOException {
    this(new BinaryConnectionFactory(), Arrays.asList(ia));
  }

  public TapConnectionProvider(List<InetSocketAddress> addrs)
    throws IOException {
    this(new BinaryConnectionFactory(), addrs);
  }

  public TapConnectionProvider(ConnectionFactory cf,
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
    conn = cf.createConnection(addrs);
    assert conn != null : "Connection factory failed to make a connection";
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
    }
  }

  protected void addOp(final Operation op) {
    conn.enqueueOperation("TStream", op);
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
