  protected int decodeIntHostOrder(byte[] data, int i) {
    return (data[i] & 0xff)
      | (data[i + 1] & 0xff) << 8
      | (data[i + 2] & 0xff) << 16
      | (data[i + 3] & 0xff) << 24;
  }

