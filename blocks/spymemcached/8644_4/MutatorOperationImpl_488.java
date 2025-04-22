  @Override
  public void initialize() {
    byte[] defBytes = new byte[8];
    defBytes[0] = (byte) ((def >> 56) & 0xff);
    defBytes[1] = (byte) ((def >> 48) & 0xff);
    defBytes[2] = (byte) ((def >> 40) & 0xff);
    defBytes[3] = (byte) ((def >> 32) & 0xff);
    defBytes[4] = (byte) ((def >> 24) & 0xff);
    defBytes[5] = (byte) ((def >> 16) & 0xff);
    defBytes[6] = (byte) ((def >> 8) & 0xff);
    defBytes[7] = (byte) (def & 0xff);
    prepareBuffer(key, 0, EMPTY_BYTES, by, defBytes, exp);
  }
