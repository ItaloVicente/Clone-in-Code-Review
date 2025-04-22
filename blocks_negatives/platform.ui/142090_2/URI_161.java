      }
      else
      {
        if (SEGMENT_PARENT.equals(stack[sp - 1])) {
			stack[sp++] = segment;
		} else {
			sp--;
		}
      }
    }
    else if (!SEGMENT_EMPTY.equals(segment) && !SEGMENT_SELF.equals(segment))
    {
      stack[sp++] = segment;
    }
    return sp;
  }

  /**
   * Finds the shortest relative or, if necessary, the absolute URI that,
   * when resolved against the given <code>base</code> absolute hierarchical
   * URI using {@link #resolve(URI) resolve}, will yield this absolute URI.
   * If <code>base</code> is non-hierarchical or is relative,
   * or <code>this</code> is non-hierarchical or is relative,
   * <code>this</code> will be returned.
   */
  public URI deresolve(URI base)
  {
    return deresolve(base, true, false, true);
  }

  /**
   * Finds an absolute URI that, when resolved against the given
   * <code>base</code> absolute hierarchical URI using {@link
   * #resolve(URI, boolean) resolve}, will yield this absolute URI.
   * If <code>base</code> is non-hierarchical or is relative,
   * or <code>this</code> is non-hierarchical or is relative,
   * <code>this</code> will be returned.
   *
   * @param preserveRootParents the boolean argument to <code>resolve(URI,
   * boolean)</code> for which the returned URI should resolve to this URI.
   * @param anyRelPath if <code>true</code>, the returned URI's path (if
   * any) will be relative, if possible.  If <code>false</code>, the form of
   * the result's path will depend upon the next parameter.
   * @param shorterRelPath if <code>anyRelPath</code> is <code>false</code>
   * and this parameter is <code>true</code>, the returned URI's path (if
   * any) will be relative, if one can be found that is no longer (by number
   * of segments) than the absolute path.  If both <code>anyRelPath</code>
   * and this parameter are <code>false</code>, it will be absolute.
   */
  public URI deresolve(URI base, boolean preserveRootParents,
                       boolean anyRelPath, boolean shorterRelPath)
  {
    if (!base.isHierarchical() || base.isRelative()) {
		return this;
	}

    if (isRelative()) {
		return this;
	}


    if (!scheme.equalsIgnoreCase(base.scheme())) {
		return this;
	}

    if (!isHierarchical()) {
		return this;
	}

    String newAuthority = authority;
    String newDevice = device;
    boolean newAbsolutePath = hasAbsolutePath();
    String[] newSegments = segments;
    String newQuery = query;

    if (equals(authority, base.authority()) &&
        (hasDevice() || hasPath() || (!base.hasDevice() && !base.hasPath())))
    {
      newAuthority = null;

      if (equals(device, base.device()) && (hasPath() || !base.hasPath()))
      {
        newDevice = null;


        if (!anyRelPath && !shorterRelPath)
        {
        }
        else if (hasPath() == base.hasPath() && segmentsEqual(base) &&
                 equals(query, base.query()))
        {
          newAbsolutePath = false;
          newSegments = NO_SEGMENTS;
          newQuery = null;
        }
        else if (!hasPath() && !base.hasPath())
        {
          newAbsolutePath = false;
          newSegments = NO_SEGMENTS;
        }
        else if (hasCollapsableSegments(preserveRootParents))
        {
        }
        else
        {
          String[] rel = findRelativePath(base, preserveRootParents);
          if (anyRelPath || segments.length > rel.length)
          {
            newAbsolutePath = false;
            newSegments = rel;
          }
        }
      }
    }

    return new URI(true, null, newAuthority, newDevice, newAbsolutePath,
                   newSegments, newQuery, fragment);
  }

  private boolean hasCollapsableSegments(boolean preserveRootParents)
  {
    if (hasRelativePath())
    {
      throw new IllegalStateException("test collapsability of relative path");
    }

    for (int i = 0, len = segments.length; i < len; i++)
    {
      String segment = segments[i];
      if ((i < len - 1 && SEGMENT_EMPTY.equals(segment)) ||
          SEGMENT_SELF.equals(segment) ||
          SEGMENT_PARENT.equals(segment) && (
            !preserveRootParents || (
              i != 0 && !SEGMENT_PARENT.equals(segments[i - 1]))))
      {
        return true;
      }
    }
    return false;
  }

  private String[] findRelativePath(URI base, boolean preserveRootParents)
  {
    if (base.hasRelativePath())
    {
      throw new IllegalArgumentException(
        "find relative path against base with relative path");
    }
    if (!hasAbsolutePath())
    {
      throw new IllegalArgumentException(
        "find relative path of non-absolute path");
    }

    String[] startPath = base.collapseSegments(preserveRootParents);
    String[] endPath = segments;

    int startCount = startPath.length > 0 ? startPath.length - 1 : 0;
    int endCount = endPath.length;

    int diff = 0;

    for (int count = startCount < endCount ? startCount : endCount - 1;
         diff < count && startPath[diff].equals(endPath[diff]); diff++)
    {
    }

    int upCount = startCount - diff;
    int downCount = endCount - diff;

    if (downCount == 1 && SEGMENT_EMPTY.equals(endPath[endCount - 1]))
    {
      downCount = 0;
    }

    if (upCount + downCount == 0)
    {
      if (query == null) {
		return new String[] { SEGMENT_SELF };
	}
      return NO_SEGMENTS;
    }

    String[] result = new String[upCount + downCount];
    Arrays.fill(result, 0, upCount, SEGMENT_PARENT);
    System.arraycopy(endPath, diff, result, upCount, downCount);
    return result;
  }

  String[] collapseSegments(boolean preserveRootParents)
  {
    if (hasRelativePath())
    {
      throw new IllegalStateException("collapse relative path");
    }

    if (!hasCollapsableSegments(preserveRootParents)) {
		return segments();
	}

    int segmentCount = segments.length;
    String[] stack = new String[segmentCount];
    int sp = 0;

    for (int i = 0; i < segmentCount; i++)
    {
      sp = accumulate(stack, sp, segments[i], preserveRootParents);
    }

    if (sp > 0 && (SEGMENT_EMPTY.equals(segments[segmentCount - 1]) ||
                   SEGMENT_PARENT.equals(segments[segmentCount - 1]) ||
                   SEGMENT_SELF.equals(segments[segmentCount - 1])))
    {
      stack[sp++] = SEGMENT_EMPTY;
    }

    String[] result = new String[sp];
    System.arraycopy(stack, 0, result, 0, sp);
    return result;
  }

  /**
   * Returns the string representation of this URI.  For a generic,
   * non-hierarchical URI, this looks like:
   * <pre>
   *   scheme:opaquePart#fragment</pre>
   *
   * <p>For a hierarchical URI, it looks like:
   * <pre>
   *
   * <p>For an <a href="#archive_explanation">archive URI</a>, it's just:
   * <pre>
   *   scheme:authority/pathSegment1/pathSegment2...?query#fragment</pre>
   * <p>Of course, absent components and their separators will be omitted.
   */
  @Override
  public String toString()
  {
    if (cachedToString == null)
    {
      StringBuilder result = new StringBuilder();
      if (!isRelative())
      {
        result.append(scheme);
        result.append(SCHEME_SEPARATOR);
      }

      if (isHierarchical())
      {
        if (hasAuthority())
        {
          if (!isArchive()) {
