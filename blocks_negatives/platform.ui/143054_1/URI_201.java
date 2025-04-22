  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the fragment component of a URI; <code>false</code> otherwise.
   *
   * <p>A fragment is taken to be unconditionally valid.
   */
  public static boolean validFragment(String value)
  {
    return true;


  }

  private static boolean contains(String s, long highBitmask, long lowBitmask)
  {
    for (int i = 0, len = s.length(); i < len; i++)
    {
      if (matches(s.charAt(i), highBitmask, lowBitmask)) {
