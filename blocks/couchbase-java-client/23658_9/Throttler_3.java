
package com.couchbase.client.internal;

import com.couchbase.client.CouchbaseConnection;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.spy.memcached.OperationFactory;

public class ThrottleManager<T extends Throttler> {

  private static final Logger LOGGER = Logger.getLogger(
    ThrottleManager.class.getName());
  private Map<InetSocketAddress, T> throttles;
  private Class<T> throttler;
  private final CouchbaseConnection conn;
  private final OperationFactory opFact;

  public ThrottleManager(List<InetSocketAddress> initialNodes,
    Class<T> throttler, CouchbaseConnection conn, OperationFactory opFact) {
    this.throttler = throttler;
    this.throttles = new HashMap<InetSocketAddress, T>();
    this.conn = conn;
    this.opFact = opFact;

    for(InetSocketAddress node : initialNodes) {
      setThrottler(node);
    }
  }

  public final ThrottleManager setThrottler(InetSocketAddress node) {
    LOGGER.log(Level.INFO, "Adding Throttler for {0}", node.toString());

    try {
      Constructor<T> constructor = throttler.getConstructor(
        this.conn.getClass(), opFact.getClass(), node.getClass());
      throttles.put(node, constructor.newInstance(this.conn, opFact, node));
    } catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Could not add Throttler for " +
        node.toString());
    }
    return this;
  }

  public T getThrottler(InetSocketAddress node) {
    return throttles.get(node);
  }

}
