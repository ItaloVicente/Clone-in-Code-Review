
package net.spy.memcached.management;

public class ClientStatsImpl implements ClientStatsMXBean {

  public long getClientInstances() {
    return Stats.CLIENTS.get();
  }

  public long getMemcachedConnections() {
    return Stats.CONNS.get();
  }

}
