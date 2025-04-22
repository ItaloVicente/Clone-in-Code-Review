
package com.couchbase.client.vbucket;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import net.spy.memcached.MemcachedNode;
import net.spy.memcached.NodeLocator;
import net.spy.memcached.compat.SpyObject;
import com.couchbase.client.vbucket.config.Config;
import com.couchbase.client.vbucket.config.ConfigDifference;

public class VBucketNodeLocator extends SpyObject implements NodeLocator {

  private final AtomicReference<TotalConfig> fullConfig;

  public VBucketNodeLocator(List<MemcachedNode> nodes, Config jsonConfig) {
    super();
    fullConfig = new AtomicReference<TotalConfig>();
    fullConfig.set(new TotalConfig(jsonConfig, fillNodesEntries(nodes)));
  }

  @Override
  public MemcachedNode getPrimary(String k) {
    TotalConfig totConfig = fullConfig.get();
    Config config = totConfig.getConfig();
    Map<String, MemcachedNode> nodesMap = totConfig.getNodesMap();
    int vbucket = config.getVbucketByKey(k);
    int serverNumber = config.getMaster(vbucket);
    String server = config.getServer(serverNumber);
    MemcachedNode pNode = nodesMap.get(server);
    if (pNode == null) {
      getLogger().error("The node locator does not have a primary for key"
        + " %s.  Wanted vbucket %s which should be on server %s.", k,
        vbucket, server);
      getLogger().error("List of nodes has %s entries:", nodesMap.size());
      Set<String> keySet = nodesMap.keySet();
      Iterator<String> iterator = keySet.iterator();
      while (iterator.hasNext()) {
        String anode = iterator.next();
        getLogger().error("MemcachedNode for %s is %s", anode,
          nodesMap.get(anode));
      }
      Collection<MemcachedNode> nodes = nodesMap.values();
      for (MemcachedNode node : nodes) {
        getLogger().error(node);
      }
    }
    assert (pNode != null);
    return pNode;
  }

  @Override
  public Iterator<MemcachedNode> getSequence(String k) {
    return new NullIterator<MemcachedNode>();
  }

  @Override
  public Collection<MemcachedNode> getAll() {
    Map<String, MemcachedNode> nodesMap = fullConfig.get().getNodesMap();
    return nodesMap.values();
  }

  @Override
  public NodeLocator getReadonlyCopy() {
    return this;
  }

  @Override
  public void updateLocator(List<MemcachedNode> nodes) {
    throw new UnsupportedOperationException("Must be updated with a config");
  }

  public void updateLocator(final List<MemcachedNode> nodes,
      final Config newconf) {
    Config current = fullConfig.get().getConfig();
    ConfigDifference compareTo = current.compareTo(newconf);
    if (compareTo.isSequenceChanged() || compareTo.getVbucketsChanges() > 0) {
      getLogger().debug("Updating configuration, received updated configuration"
        + " with significant changes.");
      fullConfig.set(new TotalConfig(newconf, fillNodesEntries(nodes)));
    } else {
      getLogger().debug("Received updated configuration with insignificant "
        + "changes.");
    }
  }

  public int getVBucketIndex(String key) {
    Config config = fullConfig.get().getConfig();
    return config.getVbucketByKey(key);
  }

  private Map<String, MemcachedNode> fillNodesEntries(
      Collection<MemcachedNode> nodes) {
    HashMap<String, MemcachedNode> vbnodesMap =
        new HashMap<String, MemcachedNode>();
    getLogger().debug("Updating nodesMap in VBucketNodeLocator.");
    for (MemcachedNode node : nodes) {
      InetSocketAddress addr = (InetSocketAddress) node.getSocketAddress();
      String address = addr.getAddress().getHostName() + ":" + addr.getPort();
      String hostname = addr.getAddress().getHostAddress() + ":"
        + addr.getPort();
      getLogger().debug("Adding node with hostname %s and address %s.",
          hostname, address);
      getLogger().debug("Node added is %s.", node);
      vbnodesMap.put(address, node);
      vbnodesMap.put(hostname, node);
    }

    return Collections.unmodifiableMap(vbnodesMap);
  }

  public MemcachedNode getAlternative(String k,
      Collection<MemcachedNode> notMyVbucketNodes) {
    Map<String, MemcachedNode> nodesMap =
        new HashMap<String, MemcachedNode>(fullConfig.get().getNodesMap());
    Collection<MemcachedNode> nodes = nodesMap.values();
    nodes.removeAll(notMyVbucketNodes);
    if (nodes.isEmpty()) {
      return null;
    } else {
      return nodes.iterator().next();
    }
  }

  private class TotalConfig {
    private Config config;
    private Map<String, MemcachedNode> nodesMap;

    public TotalConfig(Config newConfig, Map<String, MemcachedNode> newMap) {
      config = newConfig;
      nodesMap = Collections.unmodifiableMap(newMap);
    }

    protected Config getConfig() {
      return config;
    }

    protected Map<String, MemcachedNode> getNodesMap() {
      return nodesMap;
    }
  }

  class NullIterator<E> implements Iterator<MemcachedNode> {

    public boolean hasNext() {
      return false;
    }

    public MemcachedNode next() {
      throw new NoSuchElementException(
          "VBucketNodeLocators have no alternate nodes.");
    }

    public void remove() {
      throw new UnsupportedOperationException(
          "VBucketNodeLocators have no alternate nodes; cannot remove.");
    }
  }
}
