
package net.spy.memcached.management;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class OperationStatsImpl extends NotificationBroadcasterSupport
    implements OperationStatsMXBean {

  public long getTotalOps() {
    return Stats.TOTAL_OPS.get();
  }

  public long getTotalAdd() {
    return Stats.ADD_OPS.get();
  }

  public long getTotalAppend() {
    return Stats.APPEND_OPS.get();
  }

  public long getTotalCas() {
    return Stats.CAS_OPS.get();
  }

  public long getTotalDecr() {
    return Stats.DECR_OPS.get();
  }

  public long getTotalDelete() {
    return Stats.DELETE_OPS.get();
  }

  public long getTotalGet() {
    return Stats.GET_OPS.get();
  }

  public long getTotalGets() {
    return Stats.GET_OPS.get();
  }

  public long getTotalIncr() {
    return Stats.INCR_OPS.get();
  }

  public long getTotalPrepend() {
    return Stats.GET_OPS.get();
  }

  public long getTotalSet() {
    return Stats.SET_OPS.get();
  }

  public long getTotalStats() {
    return Stats.STATS_OPS.get();
  }

  public long getTotalReplace() {
    return Stats.REPLACE_OPS.get();
  }

  public void reset() {
    Stats.TOTAL_OPS.get();
    Stats.ADD_OPS.get();
    Stats.APPEND_OPS.get();
    Stats.CAS_OPS.get();
    Stats.DECR_OPS.get();
    Stats.DELETE_OPS.get();
    Stats.GET_OPS.get();
    Stats.GET_OPS.get();
    Stats.INCR_OPS.get();
    Stats.GET_OPS.get();
    Stats.SET_OPS.get();
    Stats.STATS_OPS.get();
    Stats.REPLACE_OPS.get();

    sendNotification(new Notification("ErrorCode", this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Reset error code stats"));
  }

  public void disable() {
    Stats.trackErrors = false;
    sendNotification(new AttributeChangeNotification(this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Operation Tracking disabled", "track_operations", "boolean",
        Stats.trackOps, false));
  }

  public void enable() {
    Stats.trackErrors = true;
    sendNotification(new AttributeChangeNotification(this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Operation Tracking enabled", "track_operations", "boolean",
        Stats.trackOps, true));
  }
}
