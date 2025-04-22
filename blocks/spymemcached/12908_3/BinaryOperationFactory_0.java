
package net.spy.memcached.protocol.ascii;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;

import net.spy.memcached.KeyUtil;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationState;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.ops.UnlockOperation;

final class UnlockOperationImpl extends OperationImpl implements
    UnlockOperation {

  private static final int OVERHEAD = 32;

  private static final OperationStatus UNLOCKED = new OperationStatus(true,
      "UNLOCKED");
  private static final OperationStatus NOT_FOUND = new OperationStatus(false,
      "NOT_FOUND");

  private static final String CMD = "unl";
  private final String key;
  private final long cas;

  public UnlockOperationImpl(String k, long casId,
          OperationCallback cb) {
    super(cb);
    key = k;
    cas = casId;
  }

  @Override
  public void handleLine(String line) {
    getLogger().debug("Unlock of %s returned %s", key, line);
    getCallback().receivedStatus(matchStatus(line, UNLOCKED, NOT_FOUND));
    transitionState(OperationState.COMPLETE);
  }

  @Override
  public void initialize() {
    ByteBuffer b = ByteBuffer.allocate(KeyUtil.getKeyBytes(key).length
        + OVERHEAD);
    setArguments(b, CMD, key);
    b.flip();
    setBuffer(b);
  }

  @Override
  public Collection<String> getKeys() {
    return Collections.singleton(key);
  }

  @Override
  public String toString() {
    return "Cmd: " + CMD + " Key: " + key + " Cas Value: " + cas;
  }
}
