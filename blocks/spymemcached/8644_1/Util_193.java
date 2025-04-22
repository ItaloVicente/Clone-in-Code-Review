  public static void valueToFieldOffest(byte[] buffer, int offset, int length,
      long l) {
    long divisor;
    for (int i = 0; i < length; i++) {
      divisor = (long) Math.pow(256.0, (double) (length - 1 - i));
      buffer[offset + i] = (byte) (l / divisor);
      l = l % divisor;
    }
  }
