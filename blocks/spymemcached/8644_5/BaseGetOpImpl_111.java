  @Override
  public final void handleRead(ByteBuffer b) {
    assert currentKey != null;
    assert data != null;
    assert readOffset <= data.length : "readOffset is " + readOffset
        + " data.length is " + data.length;
