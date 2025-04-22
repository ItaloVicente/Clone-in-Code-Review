
package com.couchbase.client.internal;

import com.couchbase.client.CouchbaseConnection;
import com.couchbase.client.CouchbaseProperties;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.spy.memcached.BroadcastOpFactory;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.ops.StatsOperation;
import net.spy.memcached.protocol.binary.BinaryOperationFactory;
import net.spy.memcached.compat.SpyObject;

public class AdaptiveThrottler extends SpyObject implements Throttler {

  private final int normal_stats_interval;

  private final int high_stats_interval;

  private final int critical_stats_interval;

  private final int high_sleep;

  private final int critical_sleep;

  private int interval_counter = 0;

  private CouchbaseConnection conn;

  private InetSocketAddress node;

  private ThrottlerState currentState = ThrottlerState.NORMAL;

  private BinaryOperationFactory opFact;

  public AdaptiveThrottler(CouchbaseConnection conn,
    BinaryOperationFactory opFact, InetSocketAddress node) {

    this(conn, opFact, node,
      Integer.parseInt(CouchbaseProperties.getProperty(
        "normal_stats_interval", "10000")),
      Integer.parseInt(CouchbaseProperties.getProperty(
        "high_stats_interval", "100")),
      Integer.parseInt(CouchbaseProperties.getProperty(
        "critical_stats_interval", "10")),
      Integer.parseInt(CouchbaseProperties.getProperty(
        "high_sleep_time", "1")),
      Integer.parseInt(CouchbaseProperties.getProperty(
        "critical_sleep_time", "3"))
    );

    logCreation();
  }

  public AdaptiveThrottler(CouchbaseConnection conn,
    BinaryOperationFactory opFact, InetSocketAddress node,
    int normal_stats_interval, int high_stats_interval,
    int critical_stats_interval, int high_sleep, int critical_sleep) {
    this.conn = conn;
    this.opFact = opFact;
    this.node = node;
    this.normal_stats_interval = normal_stats_interval;
    this.high_stats_interval = high_stats_interval;
    this.critical_stats_interval = critical_stats_interval;
    this.high_sleep = high_sleep;
    this.critical_sleep = critical_sleep;

    logCreation();
  }

  @Override
  public void throttle() {
    ++interval_counter;
    if(statsNeedFetch()) {
      Map<String, String> stats = gatherStats();
      int throttleTime = throttleNeeded(stats);
      if(throttleTime > 0) {
        getLogger().debug("Throttling operation for " + throttleTime + "ms");
        try {
          Thread.sleep(throttleTime);
        } catch (InterruptedException ex) {
          getLogger().warn("Interrupted while Throttling!");
          return;
        }
      }
      interval_counter = 0;
    }
  }

  private int throttleNeeded(Map<String, String> stats) {
    long high_water;
    long mem_used;

    try {
      high_water = Long.parseLong(stats.get("ep_mem_high_wat"));
      mem_used = Long.parseLong(stats.get("mem_used"));
    } catch(NumberFormatException ex) {
      getLogger().warn("Received throttle stats invalid, skipping interval.");
      return 0;
    }

    if(mem_used >= (high_water + high_water/10)) {
      currentState = ThrottlerState.CRITICAL;
      return critical_sleep;
    } else if(mem_used >= high_water) {
      currentState = ThrottlerState.HIGH;
      return high_sleep;
    } else {
      currentState = ThrottlerState.NORMAL;
      return 0;
    }
  }

  private Map<String, String> gatherStats() {
    final Map<InetSocketAddress, Map<String, String>> rv =
        new HashMap<InetSocketAddress, Map<String, String>>();

    CountDownLatch blatch = conn.broadcastOperation(new BroadcastOpFactory() {
      @Override
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        final InetSocketAddress sa = (InetSocketAddress)n.getSocketAddress();
        rv.put(sa, new HashMap<String, String>());
        return opFact.stats(null, new StatsOperation.Callback() {
          @Override
          public void gotStat(String name, String val) {
            rv.get(sa).put(name, val);
          }

          @SuppressWarnings("synthetic-access")
          @Override
          public void receivedStatus(OperationStatus status) {
            if (!status.isSuccess()) {
              getLogger().warn("Unsuccessful stats fetch: " + status);
            }
          }

          @Override
          public void complete() {
            latch.countDown();
          }
        });
      }
    });
    try {
      blatch.await(1000, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for stats", e);
    }
    return rv.get(node);
  }

  private boolean statsNeedFetch() {
    if(currentState == ThrottlerState.NORMAL
      && interval_counter >= normal_stats_interval) {
      return true;
    } else if(currentState == ThrottlerState.HIGH
      && interval_counter >= high_stats_interval) {
      return true;
    } else if(currentState == ThrottlerState.CRITICAL
      && interval_counter >= critical_stats_interval) {
      return true;
    }
    return false;
  }

  private void logCreation() {
    getLogger().info("AdaptiveThrottler instantiated with options "
      + "normal_stats_interval: " + this.normal_stats_interval
      + " high_stats_interval: " + this.high_stats_interval
      + " critical_stats_interval: " + this.critical_stats_interval
      + " high_sleep: " + this.high_sleep
      + " critical_sleep: " + this.critical_sleep
      + " - for node " + this.node);
  }

}
