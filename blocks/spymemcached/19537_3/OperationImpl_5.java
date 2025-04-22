

package net.spy.memcached.protocol.binary;

import net.spy.memcached.ObserveResponse;
import net.spy.memcached.ops.ObserveOperation;
import net.spy.memcached.ops.OperationCallback;

class ObserveOperationImpl extends SingleKeyOperationImpl implements
    ObserveOperation {

  private static final byte CMD = (byte) 0x92;

  private final long cas;
  private final String key;
  private final int index;
  private byte keystate = (byte)0xff;
  private long retCas = 0;

  public ObserveOperationImpl(String k, long c, int i,
          OperationCallback cb) {
    super(CMD, generateOpaque(), k, cb);
    cas = c;
    key = k;
    index = i;
  }

  @Override
  public void initialize() {
    prepareBuffer("", 0x0, EMPTY_BYTES, (short) index,
            (short) key.length(), key.getBytes());
  }

  @Override
  public String toString() {
    return super.toString() + " Cas: " + cas;
  }

  @Override
  protected void decodePayload(byte[] pl) {
    final short  keylen = (short) decodeShort(pl, 2);
    keystate = (byte) decodeByte(pl, keylen+4);
    retCas = (long) decodeLong(pl, keylen+5);

    ObserveResponse r = ObserveResponse.values()[keystate];

    ((ObserveOperation.Callback) getCallback()).gotData(key, retCas, r);
    getCallback().receivedStatus(STATUS_OK);
  }
}
