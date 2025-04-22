
package com.couchbase.client.internal;

import com.couchbase.client.CouchbaseConnection;
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

public class AdaptiveThrottler implements Throttler {

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

  private static final Logger LOGGER = Logger.getLogger(
    Throttler.class.getName());

  public AdaptiveThrottler(CouchbaseConnection conn, BinaryOperationFactory opFact, InetSocketAddress node) {
    this.conn = conn;
    this.opFact = opFact;
    this.node = node;
    this.normal_stats_interval = 10000;
    this.high_stats_interval = this.normal_stats_interval / 100;
    this.critical_stats_interval = this.high_stats_interval / 10;
    this.high_sleep = 1;
    this.critical_sleep = this.high_sleep * 3;
  }

  @Override
  public void throttle() {
    ++interval_counter;
    if(statsNeedFetch()) {
      Map<String, String> stats = gatherStats();
      int throttleTime = throttleNeeded(stats);
      if(throttleTime > 0) {
        LOGGER.log(Level.INFO, "Throttling op for {0}ms.", throttleTime);
        try {
          Thread.sleep(throttleTime);
        } catch (InterruptedException ex) {
          LOGGER.log(Level.WARNING, "Interrupted while Throttling.");
          return;
        }
      }
      interval_counter = 0;
    }
  }

  private int throttleNeeded(Map<String, String> stats) {
    int high_water = Integer.parseInt(stats.get("ep_mem_high_wat"));
    int mem_used = Integer.parseInt(stats.get("mem_used"));

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
              LOGGER.log(Level.WARNING, "Unsuccessful stats fetch: {0}", status);
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

}
