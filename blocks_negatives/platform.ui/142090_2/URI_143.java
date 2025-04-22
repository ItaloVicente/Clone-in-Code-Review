  private static final long MAJOR_SEPARATOR_HI = highBitmask(":/?#");
  private static final long MAJOR_SEPARATOR_LO = lowBitmask(":/?#");
  private static final long SEGMENT_END_HI = highBitmask("/?#");
  private static final long SEGMENT_END_LO = lowBitmask("/?#");

  private static final boolean ENCODE_PLATFORM_RESOURCE_URIS =
    System.getProperty("org.eclipse.emf.common.util.URI.encodePlatformResourceURIs") != null &&
    !"false".equalsIgnoreCase(System.getProperty("org.eclipse.emf.common.util.URI.encodePlatformResourceURIs"));

  static
  {
    Set<String> set = new HashSet<String>();
    String propertyValue = System.getProperty("org.eclipse.emf.common.util.URI.archiveSchemes");

    if (propertyValue == null)
    {
      set.add(SCHEME_JAR);
      set.add(SCHEME_ZIP);
      set.add(SCHEME_ARCHIVE);
    }
    else
    {
      for (StringTokenizer t = new StringTokenizer(propertyValue); t.hasMoreTokens(); )
      {
        set.add(t.nextToken().toLowerCase());
      }
    }

    archiveSchemes = Collections.unmodifiableSet(set);
  }

  private static long lowBitmask(char c)
  {
    return c < 64 ? 1L << c : 0L;
  }

  private static long highBitmask(char c)
  {
    return c >= 64 && c < 128 ? 1L << (c - 64) : 0L;
  }

  private static long lowBitmask(char from, char to)
  {
    long result = 0L;
    if (from < 64 && from <= to)
    {
      to = to < 64 ? to : 63;
      for (char c = from; c <= to; c++)
      {
        result |= (1L << c);
      }
    }
    return result;
  }

  private static long highBitmask(char from, char to)
  {
    return to < 64 ? 0 : lowBitmask((char)(from < 64 ? 0 : from - 64), (char)(to - 64));
  }

  private static long lowBitmask(String chars)
  {
    long result = 0L;
    for (int i = 0, len = chars.length(); i < len; i++)
    {
      char c = chars.charAt(i);
      if (c < 64) {
