  protected int afterKeyBytesSize() {
    if (expBytes == null) {
      return 0;
    }
    return expBytes.length + 1;
  }

  protected void afterKeyBytes(final ByteBuffer b) {
    if (expBytes != null) {
      b.put((byte) ' ');
      b.put(expBytes);
    }
  }

