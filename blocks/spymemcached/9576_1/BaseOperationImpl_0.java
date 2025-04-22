
package net.spy.memcached.ops;

public class TimedOutOperationStatus extends OperationStatus {

  public TimedOutOperationStatus() {
    super(false, "timed out");
  }
}
