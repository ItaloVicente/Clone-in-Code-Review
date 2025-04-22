
package net.spy.memcached.protocol.ascii;

import java.nio.ByteBuffer;

import net.spy.memcached.ops.TouchOperation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationState;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.KeyUtil;

final class TouchOperationImpl extends SingleKeyOperationImpl implements TouchOperation {

  private static final int OVERHEAD = 9;

  private static final OperationStatus OK = new OperationStatus(true, "TOUCHED");

  private final String key;
  private final long exp;

  public TouchOperationImpl(String k, long t, OperationCallback cb) {
    super(k, cb);
    key = k;
    exp = t;
  }

  @Override
  public void handleLine(String line) {
    getLogger().debug("Touch completed successfully");
    getCallback().receivedStatus(matchStatus(line, OK));
    transitionState(OperationState.COMPLETE);
  }

  @Override
  public void initialize() {
    ByteBuffer b = null;
    b = ByteBuffer.allocate(KeyUtil.getKeyBytes(key).length + String.valueOf(exp).length() + OVERHEAD);
    b.put(("touch " + key + " " + exp + "\r\n").getBytes());
    b.flip();
    setBuffer(b);
  }

  @Override
  public String toString() {
    return "Cmd: touch key: " + key + " exp: " + exp;
  }
}
