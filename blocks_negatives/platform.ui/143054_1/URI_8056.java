    }
    return result;
  }

  private static long highBitmask(String chars)
  {
    long result = 0L;
    for (int i = 0, len = chars.length(); i < len; i++)
    {
      char c = chars.charAt(i);
      if (c >= 64 && c < 128) {
