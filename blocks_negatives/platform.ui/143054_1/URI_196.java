  private static String toBits(long l)
  {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 64; i++)
    {
      boolean b = (l & 1L) != 0;
      result.insert(0, b ? '1' : '0');
      l >>= 1;
    }
    return result.toString();
  }
