
package com.couchbase.client;

import com.couchbase.client.vbucket.Reconfigurable;
import com.couchbase.client.vbucket.VBucketNodeLocator;
import com.couchbase.client.vbucket.config.Bucket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedConnection;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.OperationFactory;

public class CouchbaseMemcachedConnection extends MemcachedConnection  implements
  Reconfigurable {

  protected volatile boolean reconfiguring = false;
  private final CouchbaseConnectionFactory cf;

  public CouchbaseMemcachedConnection(int bufSize, CouchbaseConnectionFactory f,
      List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
      FailureMode fm, OperationFactory opfactory) throws IOException {
    super(bufSize, f, a, obs, fm, opfactory);
    this.cf = f;
  }


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
        for (MemcachedNode node : mergedNodes) {
          if (!node.isActive()) {
            queueReconnect(node);
          }
        }
        locator.updateLocator(mergedNodes);
      }

      nodesToShutdown.addAll(oddNodes);
    } catch (IOException e) {
      getLogger().error("Connection reconfiguration failed", e);
    } finally {
      reconfiguring = false;
    }
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
    getLogger().info("Shut down Couchbase client");
  }

  private void logRunException(Exception e) {
    if (shutDown) {
      getLogger().debug("Exception occurred during shutdown", e);
    } else {
      getLogger().warn("Problem handling Couchbase IO", e);
    }
  }
}
