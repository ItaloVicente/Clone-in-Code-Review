  public static long fieldToValue(byte[] buffer, int offset, int length) {
    long total = 0;
    long val = 0;
    for (int i = 0; i < length; i++) {
      val = buffer[offset + i];
      if (val < 0) {
        val = val + 256;
      }
      total += (long) Math.pow(256.0, (double) (length - 1 - i)) * val;
    }
    return total;
  }
