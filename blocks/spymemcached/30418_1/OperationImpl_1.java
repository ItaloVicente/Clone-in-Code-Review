    if (extraHeadersLength > 0) {
      addExtraHeaders(bb, extraHeaders);
    }

    bb.put(keyBytes);
    bb.put(val);

    bb.flip();
    setBuffer(bb);
  }

  private void addExtraHeaders(final ByteBuffer bb,
    final Object... extraHeaders) {
