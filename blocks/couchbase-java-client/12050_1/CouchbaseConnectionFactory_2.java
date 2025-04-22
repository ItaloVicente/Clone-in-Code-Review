
package com.couchbase.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.spy.memcached.ops.KeyedOperation;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.VBucketAware;
import com.couchbase.client.vbucket.Reconfigurable;
import com.couchbase.client.vbucket.VBucketNodeLocator;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedConnection;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.OperationFactory;
import com.couchbase.client.vbucket.config.Bucket;

public class CouchbaseConnection extends MemcachedConnection  implements
  Reconfigurable {

  public CouchbaseConnection(int bufSize, ConnectionFactory f,
      List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
      FailureMode fm, OperationFactory opfactory) throws IOException {
    super(bufSize, f, a, obs, fm, opfactory);
  }

  protected volatile boolean reconfiguring = false;

  public void reconfigure(Bucket bucket) {
    reconfiguring = true;
    try {
      List<String> servers = bucket.getConfig().getServers();
      HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
      ArrayList<InetSocketAddress> newServers =
          new ArrayList<InetSocketAddress>();
      for (String server : servers) {
        int finalColon = server.lastIndexOf(':');
        if (finalColon < 1) {
          throw new IllegalArgumentException("Invalid server ``" + server
              + "'' in vbucket's server list");
        }
        String hostPart = server.substring(0, finalColon);
        String portNum = server.substring(finalColon + 1);

        InetSocketAddress address =
            new InetSocketAddress(hostPart, Integer.parseInt(portNum));
        newServerAddresses.add(address);
        newServers.add(address);
      }

      ArrayList<MemcachedNode> oddNodes = new ArrayList<MemcachedNode>();
      ArrayList<MemcachedNode> stayNodes = new ArrayList<MemcachedNode>();
      ArrayList<InetSocketAddress> stayServers =
          new ArrayList<InetSocketAddress>();
      for (MemcachedNode current : locator.getAll()) {
        if (newServerAddresses.contains(current.getSocketAddress())) {
          stayNodes.add(current);
          stayServers.add((InetSocketAddress) current.getSocketAddress());
        } else {
          oddNodes.add(current);
        }
      }

      newServers.removeAll(stayServers);

      List<MemcachedNode> newNodes = createConnections(newServers);

      List<MemcachedNode> mergedNodes = new ArrayList<MemcachedNode>();
      mergedNodes.addAll(stayNodes);
      mergedNodes.addAll(newNodes);

      if (locator instanceof VBucketNodeLocator) {
        ((VBucketNodeLocator)locator).updateLocator(mergedNodes,
            bucket.getConfig());
      } else {
        locator.updateLocator(mergedNodes);
      }

      nodesToShutdown.addAll(oddNodes);
    } catch (IOException e) {
      getLogger().error("Connection reconfiguration failed", e);
    } finally {
      reconfiguring = false;
    }
  }

  public void addOperation(final String key, final Operation o) {
    MemcachedNode placeIn = null;
    MemcachedNode primary = locator.getPrimary(key);
    if (primary.isActive() || failureMode == FailureMode.Retry) {
      placeIn = primary;
    } else if (failureMode == FailureMode.Cancel) {
      o.cancel();
    } else {
      for (Iterator<MemcachedNode> i = locator.getSequence(key); placeIn == null
          && i.hasNext();) {
        MemcachedNode n = i.next();
        if (n.isActive()) {
          placeIn = n;
        }
      }
      if (placeIn == null) {
        placeIn = primary;
        this.getLogger().warn(
            "Could not redistribute "
                + "to another node, retrying primary node for %s.", key);
      }
    }

    assert o.isCancelled() || placeIn != null : "No node found for key " + key;
    if (placeIn != null) {
      if (locator instanceof VBucketNodeLocator) {
        VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
        short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
        if (o instanceof VBucketAware) {
          VBucketAware vbucketAwareOp = (VBucketAware) o;
          vbucketAwareOp.setVBucket(key, vbucketIndex);
          if (!vbucketAwareOp.getNotMyVbucketNodes().isEmpty()) {
            MemcachedNode alternative =
                vbucketLocator.getAlternative(key,
                    vbucketAwareOp.getNotMyVbucketNodes());
            if (alternative != null) {
              placeIn = alternative;
            }
          }
        }
      }
      addOperation(placeIn, o);
    } else {
      assert o.isCancelled() : "No node found for " + key
          + " (and not immediately cancelled)";
    }
  }

  public void addOperations(final Map<MemcachedNode, Operation> ops) {

    for (Map.Entry<MemcachedNode, Operation> me : ops.entrySet()) {
      final MemcachedNode node = me.getKey();
      Operation o = me.getValue();
      if (locator instanceof VBucketNodeLocator) {
        if (o instanceof KeyedOperation && o instanceof VBucketAware) {
          Collection<String> keys = ((KeyedOperation) o).getKeys();
          VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
          for (String key : keys) {
            short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
            VBucketAware vbucketAwareOp = (VBucketAware) o;
            vbucketAwareOp.setVBucket(key, vbucketIndex);
          }
        }
      }
      o.setHandlingNode(node);
      o.initialize();
      node.addOp(o);
      addedQueue.offer(node);
    }
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
  }

  @Override
  public void run() {
    while (running) {
      if (!reconfiguring) {
        try {
          handleIO();
        } catch (IOException e) {
          logRunException(e);
        } catch (CancelledKeyException e) {
          logRunException(e);
        } catch (ClosedSelectorException e) {
          logRunException(e);
        } catch (IllegalStateException e) {
          logRunException(e);
        }
      }
    }
    getLogger().info("Shut down membase client");
  }

  private void logRunException(Exception e) {
    if (shutDown) {
      getLogger().debug("Exception occurred during shutdown", e);
    } else {
      getLogger().warn("Problem handling membase IO", e);
    }
  }
}
