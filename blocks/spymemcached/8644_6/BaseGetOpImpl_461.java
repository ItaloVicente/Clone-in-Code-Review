  @Override
  public final void initialize() {
    int size = 6; // Enough for gets\r\n
    Collection<byte[]> keyBytes = KeyUtil.getKeyBytes(keys);
    for (byte[] k : keyBytes) {
      size += k.length;
      size++;
    }
    byte[] e = String.valueOf(exp).getBytes();
    if (hasExp) {
      size += e.length + 1;
    }
    ByteBuffer b = ByteBuffer.allocate(size);
    b.put(cmd.getBytes());
    for (byte[] k : keyBytes) {
      b.put((byte) ' ');
      b.put(k);
    }
    if (hasExp) {
      b.put((byte) ' ');
      b.put(e);
    }
    b.put(RN_BYTES);
    b.flip();
    setBuffer(b);
  }
