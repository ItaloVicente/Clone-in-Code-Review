
package net.spy.memcached.protocol.ascii;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Collection;

import net.spy.memcached.ops.TouchOperation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationState;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.KeyUtil;

final class TouchOperationImpl extends OperationImpl implements TouchOperation {

  private static final int OVERHEAD = 9;

  private static final OperationStatus OK = new OperationStatus(true, "TOUCHED");

  private final String key;
  private final long exp;

  public TouchOperationImpl(String k, long t, OperationCallback cb) {
    super(cb);
    key = k;
    exp = t;
  }

  public Collection<String> getKeys() {
    return Collections.singleton(key);
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
