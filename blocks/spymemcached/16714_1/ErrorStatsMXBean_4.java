
package net.spy.memcached.management;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import net.spy.memcached.ops.ErrorCode;

public class ErrorStatsImpl extends NotificationBroadcasterSupport
  implements ErrorStatsMXBean {

  public long getSuccessfulOps() {
    return Stats.ERROR_CODES.get(ErrorCode.SUCCESS).get();
  }

  public long getNotFoundErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_NOT_FOUND).get();
  }

  public long getExistsErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_EXISTS).get();
  }

  public long getInvalidErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_EXISTS).get();
  }

  public long getNotStoredErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_NOT_STORED).get();
  }

  public long getBadDeltaErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_DELTA_BADVAL).get();
  }

  public long getNotMyVbucketErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_NOT_MY_VBUCKET).get();
  }

  public long getAuthErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_AUTH_ERROR).get();
  }

  public long getAuthContinueErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_AUTH_CONTINUE).get();
  }

  public long getUnknownCommandErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_UNKNOWN_COMMAND).get();
  }

  public long getNoMemoryErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_NO_MEM).get();
  }

  public long getNotSupportedErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_NOT_SUPPORTED).get();
  }

  public long getInternalErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_INTERNAL).get();
  }

  public long getBusyErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_BUSY).get();
  }

  public long getTemporaryFailureErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.ERR_TEMP_FAIL).get();
  }

  public long getUnknownErrorErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.UNKNOWN_ERROR).get();
  }

  public long getCancelledErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.CANCELLED).get();
  }

  public long getTimoutErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.TIMED_OUT).get();
  }

  public long getExceptionErrors() {
    return Stats.ERROR_CODES.get(ErrorCode.EXCEPTION).get();
  }

  public void reset() {
    for (Map.Entry<ErrorCode, AtomicLong> ec: Stats.ERROR_CODES.entrySet()) {
      ec.getValue().set(0);
    }
    sendNotification(new Notification("ErrorCode", this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Reset error code stats"));
  }

  public void disable() {
    Stats.trackErrors = false;
    sendNotification(new AttributeChangeNotification(this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Error Tracking disabled", "track_errors", "boolean",
        Stats.trackErrors, false));
  }

  public void enable() {
    Stats.trackErrors = true;
    sendNotification(new AttributeChangeNotification(this,
        Stats.sequenceNumber++, System.currentTimeMillis(),
        "Error Tracking enabled", "track_errors", "boolean",
        Stats.trackErrors, true));
  }
}
