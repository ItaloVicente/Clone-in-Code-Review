  protected static final byte REQ_MAGIC = (byte) 0x80;
  protected static final byte RES_MAGIC = (byte) 0x81;
  protected static final int MIN_RECV_PACKET = 24;

  protected static final int SUCCESS = 0x00;
  protected static final int ERR_NOT_FOUND = 0x01;
  protected static final int ERR_EXISTS = 0x02;
  protected static final int ERR_2BIG = 0x03;
  protected static final int ERR_INVAL = 0x04;
  protected static final int ERR_NOT_STORED = 0x05;
  protected static final int ERR_DELTA_BADVAL = 0x06;
  protected static final int ERR_NOT_MY_VBUCKET = 0x07;
  protected static final int ERR_UNKNOWN_COMMAND = 0x81;
  protected static final int ERR_NO_MEM = 0x82;
  protected static final int ERR_NOT_SUPPORTED = 0x83;
  protected static final int ERR_INTERNAL = 0x84;
  protected static final int ERR_BUSY = 0x85;
  protected static final int ERR_TEMP_FAIL = 0x86;

  protected static final byte[] EMPTY_BYTES = new byte[0];

  protected static final OperationStatus STATUS_OK = new CASOperationStatus(
      true, "OK", CASResponse.OK);

  private static final AtomicInteger SEQ_NUMBER = new AtomicInteger(0);

  private final int cmd;
  protected short vbucket = 0;
  protected final int opaque;

  private final byte[] header = new byte[MIN_RECV_PACKET];
  private int headerOffset = 0;
  private byte[] payload = null;

  protected int keyLen;
  protected int responseCmd;
  protected int errorCode;
  protected int responseOpaque;
  protected long responseCas;

  private int payloadOffset = 0;

  protected OperationImpl(int c, int o, OperationCallback cb) {
    super();
    cmd = c;
    opaque = o;
    setCallback(cb);
  }

  protected void resetInput() {
    payload = null;
    payloadOffset = 0;
    headerOffset = 0;
  }


  @Override
  public void readFromBuffer(ByteBuffer b) throws IOException {
    if (headerOffset < MIN_RECV_PACKET) {
      int toRead = MIN_RECV_PACKET - headerOffset;
      int available = b.remaining();
      toRead = Math.min(toRead, available);
      getLogger().debug("Reading %d header bytes", toRead);
      b.get(header, headerOffset, toRead);
      headerOffset += toRead;

      if (headerOffset == MIN_RECV_PACKET) {
        int magic = header[0];
        assert magic == RES_MAGIC : "Invalid magic:  " + magic;
        responseCmd = header[1];
        assert cmd == -1 || responseCmd == cmd : "Unexpected response"
            + " command value";
        keyLen = decodeShort(header, 2);
        errorCode = decodeShort(header, 6);
        int bytesToRead = decodeInt(header, 8);
        payload = new byte[bytesToRead];
        responseOpaque = decodeInt(header, 12);
        responseCas = decodeLong(header, 16);
        assert opaqueIsValid() : "Opaque is not valid";
      }
    }

    if (headerOffset >= MIN_RECV_PACKET && payload == null) {
      finishedPayload(EMPTY_BYTES);
    } else if (payload != null) {
      int toRead = payload.length - payloadOffset;
      int available = b.remaining();
      toRead = Math.min(toRead, available);
      getLogger().debug("Reading %d payload bytes", toRead);
      b.get(payload, payloadOffset, toRead);
      payloadOffset += toRead;

      if (payloadOffset == payload.length) {
        finishedPayload(payload);
      }
    } else {
      getLogger().debug("Only read %d of the %d needed to fill a header",
          headerOffset, MIN_RECV_PACKET);
    }

  }

  protected void finishedPayload(byte[] pl) throws IOException {
    OperationStatus status = getStatusForErrorCode(errorCode, pl);

    if (status == null) {
      handleError(OperationErrorType.SERVER, new String(pl));
    } else if (errorCode == SUCCESS) {
      decodePayload(pl);
      transitionState(OperationState.COMPLETE);
    } else if (errorCode == ERR_NOT_MY_VBUCKET
        && !getState().equals(OperationState.COMPLETE)) {
      transitionState(OperationState.RETRY);
    } else {
      getCallback().receivedStatus(status);
      transitionState(OperationState.COMPLETE);
    }
  }

  protected OperationStatus getStatusForErrorCode(int errCode, byte[] errPl)
    throws IOException {
    switch (errCode) {
    case SUCCESS:
      return STATUS_OK;
    case ERR_NOT_FOUND:
      return new CASOperationStatus(false, new String(errPl),
          CASResponse.NOT_FOUND);
    case ERR_EXISTS:
      return new CASOperationStatus(false, new String(errPl),
          CASResponse.EXISTS);
    case ERR_NOT_STORED:
      return new CASOperationStatus(false, new String(errPl),
          CASResponse.NOT_FOUND);
    case ERR_2BIG:
    case ERR_INTERNAL:
      handleError(OperationErrorType.SERVER, new String(errPl));
    case ERR_INVAL:
    case ERR_DELTA_BADVAL:
    case ERR_NOT_MY_VBUCKET:
    case ERR_UNKNOWN_COMMAND:
    case ERR_NO_MEM:
    case ERR_NOT_SUPPORTED:
    case ERR_BUSY:
    case ERR_TEMP_FAIL:
      return new OperationStatus(false, new String(errPl));
    default:
      return null;
    }
  }

  protected void decodePayload(byte[] pl) {
    assert pl.length == 0 : "Payload has bytes, but decode isn't overridden";
    getCallback().receivedStatus(STATUS_OK);
  }

  protected boolean opaqueIsValid() {
    if (responseOpaque != opaque) {
      getLogger().warn("Expected opaque:  %d, got opaque:  %d\n",
          responseOpaque, opaque);
    }
    return responseOpaque == opaque;
  }

  static int decodeShort(byte[] data, int i) {
    return (data[i] & 0xff) << 8 | (data[i + 1] & 0xff);
  }

  static int decodeInt(byte[] data, int i) {
    return (data[i] & 0xff) << 24 | (data[i + 1] & 0xff) << 16
        | (data[i + 2] & 0xff) << 8 | (data[i + 3] & 0xff);
  }

  static long decodeUnsignedInt(byte[] data, int i) {
    return ((long) (data[i] & 0xff) << 24) | ((data[i + 1] & 0xff) << 16)
        | ((data[i + 2] & 0xff) << 8) | (data[i + 3] & 0xff);
  }

  static long decodeLong(byte[] data, int i) {
    return (data[i] & 0xff) << 56 | (data[i + 1] & 0xff) << 48
        | (data[i + 2] & 0xff) << 40 | (data[i + 3] & 0xff) << 32
        | (data[i + 4] & 0xff) << 24 | (data[i + 5] & 0xff) << 16
        | (data[i + 6] & 0xff) << 8 | (data[i + 7] & 0xff);
  }

  protected void prepareBuffer(String key, long cas, byte[] val,
      Object... extraHeaders) {
    int extraLen = 0;
    for (Object o : extraHeaders) {
      if (o instanceof Integer) {
        extraLen += 4;
      } else if (o instanceof byte[]) {
        extraLen += ((byte[]) o).length;
      } else if (o instanceof Long) {
        extraLen += 8;
      } else {
        assert false : "Unhandled extra header type:  " + o.getClass();
      }
    }
    final byte[] keyBytes = KeyUtil.getKeyBytes(key);
    int bufSize = MIN_RECV_PACKET + keyBytes.length + val.length;


    ByteBuffer bb = ByteBuffer.allocate(bufSize + extraLen);
    assert bb.order() == ByteOrder.BIG_ENDIAN;
    bb.put(REQ_MAGIC);
    bb.put((byte) cmd);
    bb.putShort((short) keyBytes.length);
    bb.put((byte) extraLen);
    bb.put((byte) 0); // data type
    bb.putShort(vbucket); // vbucket
    bb.putInt(keyBytes.length + val.length + extraLen);
    bb.putInt(opaque);
    bb.putLong(cas);

    for (Object o : extraHeaders) {
      if (o instanceof Integer) {
        bb.putInt((Integer) o);
      } else if (o instanceof byte[]) {
        bb.put((byte[]) o);
      } else if (o instanceof Long) {
        bb.putLong((Long) o);
      } else {
        assert false : "Unhandled extra header type:  " + o.getClass();
      }
    }

    bb.put(keyBytes);
    bb.put(val);

    bb.flip();
    setBuffer(bb);
  }

  static int generateOpaque() {
    int rv = SEQ_NUMBER.incrementAndGet();
    while (rv < 0) {
      SEQ_NUMBER.compareAndSet(rv, 0);
      rv = SEQ_NUMBER.incrementAndGet();
    }
    return rv;
  }
