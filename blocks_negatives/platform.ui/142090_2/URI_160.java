    if (hasAbsolutePath()) {
		result.append(SEGMENT_SEPARATOR);
	}

    for (int i = 0, len = segments.length; i < len; i++)
    {
      if (i != 0) {
		result.append(SEGMENT_SEPARATOR);
	}
      result.append(segments[i]);
    }
    return result.toString();
  }

  /**
   * If this is a hierarchical URI with a query component, returns it;
   * <code>null</code> otherwise.
   */
  public String query()
  {
    return query;
  }


  /**
   * Returns the URI formed from this URI and the given query.
   *
   * @exception java.lang.IllegalArgumentException if
   * <code>query</code> is not a valid query (portion) according
   * to {@link #validQuery validQuery}.
   */
  public URI appendQuery(String query)
  {
    if (!validQuery(query))
    {
      throw new IllegalArgumentException(
        "invalid query portion: " + query);
    }
    return new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, fragment);
  }

  /**
   * If this URI has a non-null {@link #query query}, returns the URI
   * formed by removing it; this URI unchanged, otherwise.
   */
  public URI trimQuery()
  {
    if (query == null)
    {
      return this;
    }
    else
    {
      return new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, null, fragment);
    }
  }

  /**
   * If this URI has a fragment component, returns it; <code>null</code>
   * otherwise.
   */
  public String fragment()
  {
    return fragment;
  }

  /**
   * Returns the URI formed from this URI and the given fragment.
   *
   * @exception java.lang.IllegalArgumentException if
   * <code>fragment</code> is not a valid fragment (portion) according
   * to {@link #validFragment validFragment}.
   */
  public URI appendFragment(String fragment)
  {
    if (!validFragment(fragment))
    {
      throw new IllegalArgumentException(
        "invalid fragment portion: " + fragment);
    }
    URI result = new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, fragment);

    if (!hasFragment())
    {
      result.cachedTrimFragment = this;
    }
    return result;
  }

  /**
   * If this URI has a non-null {@link #fragment fragment}, returns the URI
   * formed by removing it; this URI unchanged, otherwise.
   */
  public URI trimFragment()
  {
    if (fragment == null)
    {
      return this;
    }
    else if (cachedTrimFragment == null)
    {
      cachedTrimFragment = new URI(isHierarchical(), scheme, authority, device, hasAbsolutePath(), segments, query, null);
    }

    return cachedTrimFragment;
  }

  /**
   * Resolves this URI reference against a <code>base</code> absolute
   * hierarchical URI, returning the resulting absolute URI.  If already
   * absolute, the URI itself is returned.  URI resolution is described in
   * 2396</a>, "Resolving Relative References to Absolute Form."
   *
   * <p>During resolution, empty segments, self references ("."), and parent
   * references ("..") are interpreted, so that they can be removed from the
   * path.  Step 6(g) gives a choice of how to handle the case where parent
   * references point to a path above the root: the offending segments can
   * be preserved or discarded.  This method preserves them.  To have them
   * discarded, please use the two-parameter form of {@link
   * #resolve(URI, boolean) resolve}.
   *
   * @exception java.lang.IllegalArgumentException if <code>base</code> is
   * non-hierarchical or is relative.
   */
  public URI resolve(URI base)
  {
    return resolve(base, true);
  }

  /**
   * Resolves this URI reference against a <code>base</code> absolute
   * hierarchical URI, returning the resulting absolute URI.  If already
   * absolute, the URI itself is returned.  URI resolution is described in
   * 2396</a>, "Resolving Relative References to Absolute Form."
   *
   * <p>During resolution, empty segments, self references ("."), and parent
   * references ("..") are interpreted, so that they can be removed from the
   * path.  Step 6(g) gives a choice of how to handle the case where parent
   * references point to a path above the root: the offending segments can
   * be preserved or discarded.  This method can do either.
   *
   * @param preserveRootParents <code>true</code> if segments referring to the
   * parent of the root path are to be preserved; <code>false</code> if they
   * are to be discarded.
   *
   * @exception java.lang.IllegalArgumentException if <code>base</code> is
   * non-hierarchical or is relative.
   */
  public URI resolve(URI base, boolean preserveRootParents)
  {
    if (!base.isHierarchical() || base.isRelative())
    {
      throw new IllegalArgumentException(
        "resolve against non-hierarchical or relative base");
    }

    if (!isRelative()) {
		return this;
	}


    String newAuthority = authority;
    String newDevice = device;
    boolean newAbsolutePath = hasAbsolutePath();
    String[] newSegments = segments;
    String newQuery = query;

    if (authority == null)
    {
      newAuthority = base.authority();

      if (device == null)
      {
        newDevice = base.device();

        if (hasEmptyPath() && query == null)
        {
          newAbsolutePath = base.hasAbsolutePath();
          newSegments = base.segments();
          newQuery = base.query();
        }
        else if (hasRelativePath())
        {
          newAbsolutePath = base.hasAbsolutePath() || !hasEmptyPath();
          newSegments = newAbsolutePath ? mergePath(base, preserveRootParents)
            : NO_SEGMENTS;
        }
      }
    }

    return new URI(true, base.scheme(), newAuthority, newDevice,
                   newAbsolutePath, newSegments, newQuery, fragment);
  }

  private String[] mergePath(URI base, boolean preserveRootParents)
  {
    if (base.hasRelativePath())
    {
      throw new IllegalArgumentException("merge against relative path");
    }
    if (!hasRelativePath())
    {
      throw new IllegalStateException("merge non-relative path");
    }

    int baseSegmentCount = base.segmentCount();
    int segmentCount = segments.length;
    String[] stack = new String[baseSegmentCount + segmentCount];
    int sp = 0;

    for (int i = 0; i < baseSegmentCount - 1; i++)
    {
      sp = accumulate(stack, sp, base.segment(i), preserveRootParents);
    }

    for (int i = 0; i < segmentCount; i++)
    {
      sp = accumulate(stack, sp, segments[i], preserveRootParents);
    }

    if (sp > 0 &&  (segmentCount == 0 ||
                    SEGMENT_EMPTY.equals(segments[segmentCount - 1]) ||
                    SEGMENT_PARENT.equals(segments[segmentCount - 1]) ||
                    SEGMENT_SELF.equals(segments[segmentCount - 1])))
    {
      stack[sp++] = SEGMENT_EMPTY;
    }

    String[] result = new String[sp];
    System.arraycopy(stack, 0, result, 0, sp);
    return result;
  }

  private static int accumulate(String[] stack, int sp, String segment,
                                boolean preserveRootParents)
  {
    if (SEGMENT_PARENT.equals(segment))
    {
      if (sp == 0)
      {
        if (preserveRootParents) {
			stack[sp++] = segment;
