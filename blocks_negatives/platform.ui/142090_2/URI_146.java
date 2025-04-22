  /**
   * Static factory method for a generic, non-hierarchical URI.  There is no
   * concept of a relative non-hierarchical URI; such an object cannot be
   * created.
   *
   * @exception java.lang.IllegalArgumentException if <code>scheme</code> is
   * null, if <code>scheme</code> is an <a href="#archive_explanation">archive
   * URI</a> scheme, or if <code>scheme</code>, <code>opaquePart</code>, or
   * <code>fragment</code> is not valid according to {@link #validScheme
   * validScheme}, {@link #validOpaquePart validOpaquePart}, or {@link
   * #validFragment validFragment}, respectively.
   */
  public static URI createGenericURI(String scheme, String opaquePart,
                                     String fragment)
  {
    if (scheme == null)
    {
      throw new IllegalArgumentException("relative non-hierarchical URI");
    }

    if (isArchiveScheme(scheme))
    {
      throw new IllegalArgumentException("non-hierarchical archive URI");
    }

    validateURI(false, scheme, opaquePart, null, false, NO_SEGMENTS, null, fragment);
    return new URI(false, scheme, opaquePart, null, false, NO_SEGMENTS, null, fragment);
  }

  /**
   * Static factory method for a hierarchical URI with no path.  The
   * URI will be relative if <code>scheme</code> is non-null, and absolute
   * otherwise.  An absolute URI with no path requires a non-null
   * <code>authority</code> and/or <code>device</code>.
   *
   * @exception java.lang.IllegalArgumentException if <code>scheme</code> is
   * non-null while <code>authority</code> and <code>device</code> are null,
   * if <code>scheme</code> is an <a href="#archive_explanation">archive
   * URI</a> scheme, or if <code>scheme</code>, <code>authority</code>,
   * <code>device</code>, <code>query</code>, or <code>fragment</code> is not
   * valid according to {@link #validScheme validSheme}, {@link
   * #validAuthority validAuthority}, {@link #validDevice validDevice},
   * {@link #validQuery validQuery}, or {@link #validFragment validFragment},
   * respectively.
   */
  public static URI createHierarchicalURI(String scheme, String authority,
                                          String device, String query,
                                          String fragment)
  {
    if (scheme != null && authority == null && device == null)
    {
      throw new IllegalArgumentException(
        "absolute hierarchical URI without authority, device, path");
    }

    if (isArchiveScheme(scheme))
    {
      throw new IllegalArgumentException("archive URI with no path");
    }

    validateURI(true, scheme, authority, device, false, NO_SEGMENTS, query, fragment);
    return new URI(true, scheme, authority, device, false, NO_SEGMENTS, query, fragment);
  }

  /**
   * Static factory method for a hierarchical URI with absolute path.
   * The URI will be relative if <code>scheme</code> is non-null, and
   * absolute otherwise.
   *
   * @param segments an array of non-null strings, each representing one
   * segment of the path.  As an absolute path, it is automatically
   * preceded by a <code>/</code> separator.  If desired, a trailing
   * separator should be represented by an empty-string segment as the last
   * element of the array.
   *
   * @exception java.lang.IllegalArgumentException if <code>scheme</code> is
   * an <a href="#archive_explanation">archive URI</a> scheme and
   * <code>device</code> is non-null, or if <code>scheme</code>,
   * <code>authority</code>, <code>device</code>, <code>segments</code>,
   * <code>query</code>, or <code>fragment</code> is not valid according to
   * {@link #validScheme validScheme}, {@link #validAuthority validAuthority}
   * or {@link #validArchiveAuthority validArchiveAuthority}, {@link
   * #validDevice validDevice}, {@link #validSegments validSegments}, {@link
   * #validQuery validQuery}, or {@link #validFragment validFragment}, as
   * appropriate.
   */
  public static URI createHierarchicalURI(String scheme, String authority,
                                          String device, String[] segments,
                                          String query, String fragment)
  {
    if (isArchiveScheme(scheme) && device != null)
    {
      throw new IllegalArgumentException("archive URI with device");
    }

    segments = fix(segments);
    validateURI(true, scheme, authority, device, true, segments, query, fragment);
    return new URI(true, scheme, authority, device, true, segments, query, fragment);
  }

  /**
   * Static factory method for a relative hierarchical URI with relative
   * path.
   *
   * @param segments an array of non-null strings, each representing one
   * segment of the path.  A trailing separator is represented by an
   * empty-string segment at the end of the array.
   *
   * @exception java.lang.IllegalArgumentException if <code>segments</code>,
   * <code>query</code>, or <code>fragment</code> is not valid according to
   * {@link #validSegments validSegments}, {@link #validQuery validQuery}, or
   * {@link #validFragment validFragment}, respectively.
   */
  public static URI createHierarchicalURI(String[] segments, String query,
                                          String fragment)
  {
    segments = fix(segments);
    validateURI(true, null, null, null, false, segments, query, fragment);
    return new URI(true, null, null, null, false, segments, query, fragment);
  }

  private static String[] fix(String[] segments)
  {
    return segments == null ? NO_SEGMENTS : (String[])segments.clone();
  }

  /**
   * Static factory method based on parsing a URI string, with
   * <a href="#device_explanation">explicit device support</a> and handling
   * for <a href="#archive_explanation">archive URIs</a> enabled. The
   * specified string is parsed as described in <a
   * appropriate <code>URI</code> is created and returned.  Note that
   * validity testing is not as strict as in the RFC; essentially, only
   * separator characters are considered.  This method also does not perform
   * encoding of invalid characters, so it should only be used when the URI
   * string is known to have already been encoded, so as to avoid double
   * encoding.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from <code>uri</code> is not valid according to {@link #validScheme
   * validScheme}, {@link #validOpaquePart validOpaquePart}, {@link
   * #validAuthority validAuthority}, {@link #validArchiveAuthority
   * validArchiveAuthority}, {@link #validDevice validDevice}, {@link
   * #validSegments validSegments}, {@link #validQuery validQuery}, or {@link
   * #validFragment validFragment}, as appropriate.
   */
  public static URI createURI(String uri)
  {
    return createURIWithCache(uri);
  }

  /**
   * Static factory method that encodes and parses the given URI string.
   * Appropriate encoding is performed for each component of the URI.
   * If more than one <code>#</code> is in the string, the last one is
   * assumed to be the fragment's separator, and any others are encoded.
   * This method is the simplest way to safely parse an arbitrary URI string.
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  This
   * capability is provided to allow partially encoded URIs to be "fixed",
   * while avoiding adding double encoding; however, it is usual just to
   * specify <code>false</code> to perform ordinary encoding.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from <code>uri</code> is not valid according to {@link #validScheme
   * validScheme}, {@link #validOpaquePart validOpaquePart}, {@link
   * #validAuthority validAuthority}, {@link #validArchiveAuthority
   * validArchiveAuthority}, {@link #validDevice validDevice}, {@link
   * #validSegments validSegments}, {@link #validQuery validQuery}, or {@link
   * #validFragment validFragment}, as appropriate.
   */
  public static URI createURI(String uri, boolean ignoreEscaped)
  {
    return createURIWithCache(encodeURI(uri, ignoreEscaped, FRAGMENT_LAST_SEPARATOR));
  }

  /**
   * When specified as the last argument to {@link #createURI(String, boolean, int)
   * createURI}, indicates that there is no fragment, so any <code>#</code> characters
   * should be encoded.
   * @see #createURI(String, boolean, int)
   */
  public static final int FRAGMENT_NONE = 0;

  /**
   * When specified as the last argument to {@link #createURI(String, boolean, int)
   * createURI}, indicates that the first <code>#</code> character should be taken as
   * the fragment separator, and any others should be encoded.
   * @see #createURI(String, boolean, int)
   */
  public static final int FRAGMENT_FIRST_SEPARATOR = 1;

  /**
   * When specified as the last argument to {@link #createURI(String, boolean, int)
   * createURI}, indicates that the last <code>#</code> character should be taken as
   * the fragment separator, and any others should be encoded.
   * @see #createURI(String, boolean, int)
   */
  public static final int FRAGMENT_LAST_SEPARATOR = 2;

  /**
   * Static factory method that encodes and parses the given URI string.
   * Appropriate encoding is performed for each component of the URI.
   * Control is provided over which, if any, <code>#</code> should be
   * taken as the fragment separator and which should be encoded.
   * This method is the preferred way to safely parse an arbitrary URI string
   * that is known to contain <code>#</code> characters in the fragment or to
   * have no fragment at all.
   *
   * @param ignoreEscaped <code>true</code> to leave <code>%</code> characters
   * unescaped if they already begin a valid three-character escape sequence;
   * <code>false</code> to encode all <code>%</code> characters.  This
   * capability is provided to allow partially encoded URIs to be "fixed",
   * while avoiding adding double encoding; however, it is usual just to
   * specify <code>false</code> to perform ordinary encoding.
   *
   * @param fragmentLocationStyle one of {@link #FRAGMENT_NONE},
   * {@link #FRAGMENT_FIRST_SEPARATOR}, or {@link #FRAGMENT_LAST_SEPARATOR},
   * indicating which, if any, of the <code>#</code> characters should be
   * considered the fragment separator. Any others will be encoded.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from <code>uri</code> is not valid according to {@link #validScheme
   * validScheme}, {@link #validOpaquePart validOpaquePart}, {@link
   * #validAuthority validAuthority}, {@link #validArchiveAuthority
   * validArchiveAuthority}, {@link #validDevice validDevice}, {@link
   * #validSegments validSegments}, {@link #validQuery validQuery}, or {@link
   * #validFragment validFragment}, as appropriate.
   */
  public static URI createURI(String uri, boolean ignoreEscaped, int fragmentLocationStyle)
  {
    return createURIWithCache(encodeURI(uri, ignoreEscaped, fragmentLocationStyle));
  }

  /**
   * Static factory method based on parsing a URI string, with
   * <a href="#device_explanation">explicit device support</a> enabled.
   * Note that validity testing is not a strict as in the RFC; essentially,
   * only separator characters are considered.  So, for example, non-Latin
   * alphabet characters appearing in the scheme would not be considered an
   * error.
   *
   * @exception java.lang.IllegalArgumentException if any component parsed
   * from <code>uri</code> is not valid according to {@link #validScheme
   * validScheme}, {@link #validOpaquePart validOpaquePart}, {@link
   * #validAuthority validAuthority}, {@link #validArchiveAuthority
   * validArchiveAuthority}, {@link #validDevice validDevice}, {@link
   * #validSegments validSegments}, {@link #validQuery validQuery}, or {@link
   * #validFragment validFragment}, as appropriate.
   *
   * @deprecated Use {@link #createURI(String) createURI}, which now has explicit
   * device support enabled. The two methods now operate identically.
   */
  @Deprecated
  public static URI createDeviceURI(String uri)
  {
    return createURIWithCache(uri);
  }

  /**
   * This method was included in the public API by mistake.
   *
   * @deprecated Please use {@link #createURI(String) createURI} instead.
   */
  @Deprecated
  public static URI createURIWithCache(String uri)
  {
    int i = uri.indexOf(FRAGMENT_SEPARATOR);
    String base = i == -1 ? uri : uri.substring(0, i);
    String fragment = i == -1 ? null : uri.substring(i + 1);

    URI result = uriCache.get(base);

    if (result == null)
    {
      result = parseIntoURI(base);
      uriCache.put(base, result);
    }

    if (fragment != null)
    {
      result = result.appendFragment(fragment);
    }
    return result;
  }

  private static URI parseIntoURI(String uri)
  {
    boolean hierarchical = true;
    String scheme = null;
    String authority = null;
    String device = null;
    boolean absolutePath = false;
    String[] segments = NO_SEGMENTS;
    String query = null;
    String fragment = null;

    int i = 0;
    int j = find(uri, i, MAJOR_SEPARATOR_HI, MAJOR_SEPARATOR_LO);

    if (j < uri.length() && uri.charAt(j) == SCHEME_SEPARATOR)
    {
      scheme = uri.substring(i, j);
      i = j + 1;
    }

    boolean archiveScheme = isArchiveScheme(scheme);
    if (archiveScheme)
    {
      j = uri.lastIndexOf(ARCHIVE_SEPARATOR);
      if (j == -1)
      {
        throw new IllegalArgumentException("no archive separator");
      }
      hierarchical = true;
      authority = uri.substring(i, ++j);
      i = j;
    }
    else if (uri.startsWith(AUTHORITY_SEPARATOR, i))
    {
      i += AUTHORITY_SEPARATOR.length();
      j = find(uri, i, SEGMENT_END_HI, SEGMENT_END_LO);
      authority = uri.substring(i, j);
      i = j;
    }
    else if (scheme != null &&
             (i == uri.length() || uri.charAt(i) != SEGMENT_SEPARATOR))
    {
      hierarchical = false;
      j = uri.indexOf(FRAGMENT_SEPARATOR, i);
      if (j == -1) {
		j = uri.length();
	}
      authority = uri.substring(i, j);
      i = j;
    }

    if (!archiveScheme && i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
    {
      j = find(uri, i + 1, SEGMENT_END_HI, SEGMENT_END_LO);
      String s = uri.substring(i + 1, j);

      if (s.length() > 0 && s.charAt(s.length() - 1) == DEVICE_IDENTIFIER)
      {
        device = s;
        i = j;
      }
    }

    if (i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
    {
      i++;
      absolutePath = true;
    }

    if (segmentsRemain(uri, i))
    {
      List<String> segmentList = new ArrayList<String>();

      while (segmentsRemain(uri, i))
      {
        j = find(uri, i, SEGMENT_END_HI, SEGMENT_END_LO);
        segmentList.add(uri.substring(i, j));
        i = j;

        if (i < uri.length() && uri.charAt(i) == SEGMENT_SEPARATOR)
        {
          if (!segmentsRemain(uri, ++i)) {
			segmentList.add(SEGMENT_EMPTY);
		}
        }
      }
      segments = new String[segmentList.size()];
      segmentList.toArray(segments);
    }

    if (i < uri.length() && uri.charAt(i) == QUERY_SEPARATOR)
    {
      j = uri.indexOf(FRAGMENT_SEPARATOR, ++i);
      if (j == -1) {
		j = uri.length();
	}
      query = uri.substring(i, j);
      i = j;
    }

    {
      fragment = uri.substring(++i);
    }

    validateURI(hierarchical, scheme, authority, device, absolutePath, segments, query, fragment);
    return new URI(hierarchical, scheme, authority, device, absolutePath, segments, query, fragment);
  }

  private static boolean segmentsRemain(String uri, int i)
  {
    return i < uri.length() && uri.charAt(i) != QUERY_SEPARATOR &&
      uri.charAt(i) != FRAGMENT_SEPARATOR;
  }

  private static int find(String s, int i, long highBitmask, long lowBitmask)
  {
    int len = s.length();
    if (i >= len) {
		return len;
	}

    for (i = i > 0 ? i : 0; i < len; i++)
    {
      if (matches(s.charAt(i), highBitmask, lowBitmask)) {
