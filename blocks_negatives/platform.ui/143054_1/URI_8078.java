    }

    if (i == segments.length - 1 && SEGMENT_EMPTY.equals(segments[i]))
    {
      return NO_SEGMENTS;
    }

    String[] newSegments = new String[segments.length - i];
    System.arraycopy(segments, i, newSegments, 0, newSegments.length);
    return newSegments;
  }

  /**
   * Encodes a string so as to produce a valid opaque part value, as defined
   * by the RFC.  All excluded characters, such as space and <code>#</code>,
   * are escaped, as is <code>/</code> if it is the first character.
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  Note that
   * if a <code>%</code> is not followed by 2 hex digits, it will always be
   * escaped.
   */
  public static String encodeOpaquePart(String value, boolean ignoreEscaped)
  {
    String result = encode(value, URIC_HI, URIC_LO, ignoreEscaped);
    return result != null && result.length() > 0 && result.charAt(0) == SEGMENT_SEPARATOR ?
      "%2F" + result.substring(1) :
      result;
  }

  /**
   * Encodes a string so as to produce a valid authority, as defined by the
   * RFC.  All excluded characters, such as space and <code>#</code>,
   * are escaped, as are <code>/</code> and <code>?</code>
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  Note that
   * if a <code>%</code> is not followed by 2 hex digits, it will always be
   * escaped.
   */
  public static String encodeAuthority(String value, boolean ignoreEscaped)
  {
    return encode(value, SEGMENT_CHAR_HI, SEGMENT_CHAR_LO, ignoreEscaped);
  }

  /**
   * Encodes a string so as to produce a valid segment, as defined by the
   * RFC.  All excluded characters, such as space and <code>#</code>,
   * are escaped, as are <code>/</code> and <code>?</code>
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  Note that
   * if a <code>%</code> is not followed by 2 hex digits, it will always be
   * escaped.
   */
  public static String encodeSegment(String value, boolean ignoreEscaped)
  {
    return encode(value, SEGMENT_CHAR_HI, SEGMENT_CHAR_LO, ignoreEscaped);
  }

  /**
   * Encodes a string so as to produce a valid query, as defined by the RFC.
   * Only excluded characters, such as space and <code>#</code>, are escaped.
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  Note that
   * if a <code>%</code> is not followed by 2 hex digits, it will always be
   * escaped.
   */
  public static String encodeQuery(String value, boolean ignoreEscaped)
  {
    return encode(value, URIC_HI, URIC_LO, ignoreEscaped);
  }

  /**
   * Encodes a string so as to produce a valid fragment, as defined by the
   * RFC.  Only excluded characters, such as space and <code>#</code>, are
   * escaped.
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  Note that
   * if a <code>%</code> is not followed by 2 hex digits, it will always be
   * escaped.
   */
  public static String encodeFragment(String value, boolean ignoreEscaped)
  {
    return encode(value, URIC_HI, URIC_LO, ignoreEscaped);
  }

  private static String encodeURI(String uri, boolean ignoreEscaped, int fragmentLocationStyle)
  {
    if (uri == null) {
		return null;
