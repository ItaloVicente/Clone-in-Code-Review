  public byte[] encodeNum(long l, int maxBytes) {
    byte[] rv = new byte[maxBytes];
    for (int i = 0; i < rv.length; i++) {
      int pos = rv.length - i - 1;
      rv[pos] = (byte) ((l >> (8 * i)) & 0xff);
    }
    if (packZeros) {
      int firstNon0 = 0;
      
      while (firstNon0 < rv.length && rv[firstNon0] == 0) {
        firstNon0++;
      }
      if (firstNon0 > 0) {
        byte[] tmp = new byte[rv.length - firstNon0];
        System.arraycopy(rv, firstNon0, tmp, 0, rv.length - firstNon0);
        rv = tmp;
      }
    }
    return rv;
  }
