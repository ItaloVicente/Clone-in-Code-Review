  @Override
  public void initialize() {
    ByteBuffer bb = ByteBuffer.allocate(data.length
        + KeyUtil.getKeyBytes(key).length + OVERHEAD);
    setArguments(bb, type, key, flags, exp, data.length);
    assert bb.remaining() >= data.length + 2 : "Not enough room in buffer,"
        + " need another " + (2 + data.length - bb.remaining());
    bb.put(data);
    bb.put(CRLF);
    bb.flip();
    setBuffer(bb);
  }
