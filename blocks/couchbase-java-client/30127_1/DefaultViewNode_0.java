
package com.couchbase.client;

import com.couchbase.client.DefaultViewNode.EventLogger;
import com.couchbase.client.DefaultViewNode.MyHttpRequestExecutionHandler;
import com.couchbase.client.http.AsyncConnectionManager;
import com.couchbase.client.http.RequeueOpCallback;
import com.couchbase.client.protocol.views.HttpOperation;
import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.DefaultConfig;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.compat.SpyObject;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.nio.protocol.AsyncNHttpClientHandler;
import org.apache.http.nio.util.DirectByteBufferAllocator;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;


public class DefaultViewConnection extends SpyObject implements ViewConnection {
  private static final int NUM_CONNS = 1;
  private static final int MAX_ADDOP_RETRIES = 6;

  private volatile boolean shutDown = false;
  protected volatile boolean reconfiguring = false;
  protected volatile boolean running = true;

  private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
  private final Lock rlock = rwlock.readLock();
  private final Lock wlock = rwlock.writeLock();

  private final CouchbaseConnectionFactory connFactory;
  private final Collection<ConnectionObserver> connObservers =
    new ConcurrentLinkedQueue<ConnectionObserver>();
  private List<DefaultViewNode> couchNodes;
  private int nextNode;

  public DefaultViewConnection(CouchbaseConnectionFactory cf, List<InetSocketAddress> addrs, Collection<ConnectionObserver> obs)
    throws IOException {
    connFactory = cf;
    connObservers.addAll(obs);
    couchNodes = createConnections(addrs);
    nextNode = 0;
  }

  private List<DefaultViewNode> createConnections(List<InetSocketAddress> addrs)
    throws IOException {

    List<DefaultViewNode> nodeList = new LinkedList<DefaultViewNode>();

    for (InetSocketAddress a : addrs) {
      HttpParams params = new SyncBasicHttpParams();
      params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
          .setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000)
          .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
          .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,
              false)
          .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
          .setParameter(CoreProtocolPNames.USER_AGENT,
              "Couchbase Java Client 1.0.2");

      HttpProcessor httpproc =
          new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
            new RequestContent(), new RequestTargetHost(),
            new RequestConnControl(), new RequestUserAgent(),
            new RequestExpectContinue(), });

      AsyncNHttpClientHandler protocolHandler =
          new AsyncNHttpClientHandler(httpproc,
              new MyHttpRequestExecutionHandler(this),
              new DefaultConnectionReuseStrategy(),
              new DirectByteBufferAllocator(), params);
      protocolHandler.setEventListener(new EventLogger());

      AsyncConnectionManager connMgr =
          new AsyncConnectionManager(
              new HttpHost(a.getHostName(), a.getPort()), NUM_CONNS,
              protocolHandler, params, new RequeueOpCallback(this));
      getLogger().info("Added %s to connect queue", a.getHostName());

      DefaultViewNode node = connFactory.createDefaultViewNode(a, connMgr);
      node.init();
      nodeList.add(node);
    }

    return nodeList;
  }

  @Override
  public void addOp(final HttpOperation op) {
    rlock.lock();
    try {
      if (couchNodes.isEmpty()) {
        getLogger().error("No server connections. Cancelling op.");
        op.cancel();
      } else {
        boolean success = false;
        int retries = 0;
        do {
          if(retries >= MAX_ADDOP_RETRIES) {
            op.cancel();
            break;
          }
          DefaultViewNode node = couchNodes.get(getNextNode());
          if(node.isShuttingDown() || !hasActiveVBuckets(node)) {
            continue;
          }
          if(retries > 0) {
            getLogger().debug("Retrying view operation #" + op.hashCode()
              + " on node: " + node.getSocketAddress());
          }
          success = node.writeOp(op);
          if(retries > 0 && success) {
            getLogger().debug("Successfully wrote #" + op.hashCode()
              + " on node: " + node.getSocketAddress() + " after "
              + retries + " retries.");
          }
          retries++;
        } while(!success);
      }
    } finally {
      rlock.unlock();
    }
  }

  private int getNextNode() {
    return nextNode = (++nextNode % couchNodes.size());
  }

  private boolean hasActiveVBuckets(DefaultViewNode node) {
    DefaultConfig config = (DefaultConfig) connFactory.getVBucketConfig();
    return config.nodeHasActiveVBuckets(node.getSocketAddress());
  }

  @Override
  public List<DefaultViewNode> getConnectedNodes() {
    return couchNodes;
  }

  public void checkState() {
    if (shutDown) {
      throw new IllegalStateException("Shutting down");
    }
  }

  @Override
  public boolean shutdown() throws IOException {
    if (shutDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }

    shutDown = true;
    running = false;

    List<DefaultViewNode> nodesToRemove = new ArrayList<DefaultViewNode>();
    for(DefaultViewNode node : couchNodes) {
      if (node != null) {
        String hostname = node.getSocketAddress().getHostName();
        if (node.hasWriteOps()) {
          getLogger().warn("Shutting down " + hostname
            + " with ops waiting to be written");
        } else {
          getLogger().info("Node " + hostname
            + " has no ops in the queue");
        }
        node.shutdown();
        nodesToRemove.add(node);
      }
    }

    for(DefaultViewNode node : nodesToRemove) {
      couchNodes.remove(node);
    }

    return true;
  }

  public void reconfigure(Bucket bucket) {
    reconfiguring = true;

    try {
      HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
      List<InetSocketAddress> newServers =
        AddrUtil.getAddressesFromURL(bucket.getConfig().getCouchServers());
      for (InetSocketAddress server : newServers) {
        newServerAddresses.add(server);
      }

      ArrayList<DefaultViewNode> shutdownNodes = new ArrayList<DefaultViewNode>();
      ArrayList<DefaultViewNode> stayNodes = new ArrayList<DefaultViewNode>();
      ArrayList<InetSocketAddress> stayServers =
          new ArrayList<InetSocketAddress>();

      wlock.lock();
      try {
        for (DefaultViewNode current : couchNodes) {
          if (newServerAddresses.contains(current.getSocketAddress())) {
            stayNodes.add(current);
            stayServers.add(current.getSocketAddress());
          } else {
            shutdownNodes.add(current);
          }
        }

        newServers.removeAll(stayServers);

        List<DefaultViewNode> newNodes = createConnections(newServers);

        List<DefaultViewNode> mergedNodes = new ArrayList<DefaultViewNode>();
        mergedNodes.addAll(stayNodes);
        mergedNodes.addAll(newNodes);

        couchNodes = mergedNodes;
      } finally {
        wlock.unlock();
      }

      for (DefaultViewNode qa : shutdownNodes) {
        try {
          qa.shutdown();
        } catch (IOException e) {
          getLogger().error("Error shutting down connection to "
              + qa.getSocketAddress());
        }
      }

    } catch (IOException e) {
      getLogger().error("Connection reconfiguration failed", e);
    } finally {
      reconfiguring = false;
    }
  }
}
