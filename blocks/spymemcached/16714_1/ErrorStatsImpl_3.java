
package net.spy.memcached.management;

public interface ClientStatsMXBean {
  long getClientInstances();
  long getMemcachedConnections();
}
