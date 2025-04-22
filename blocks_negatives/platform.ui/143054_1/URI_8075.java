    if (hasAbsolutePath()) {
		result.append(separator);
	}

    for (int i = 0, len = segments.length; i < len; i++)
    {
      if (i != 0) {
		result.append(separator);
	}
      result.append(segments[i]);
    }

    return decode(result.toString());
  }

  /**
   * If this is a platform URI, as determined by {@link #isPlatform}, returns
   * the workspace-relative or plug-in-based path to the resource, optionally
   * {@link #decode decoding} the segments in the process.
   * @see #createPlatformResourceURI(String, boolean)
   * @see #createPlatformPluginURI
   * @since org.eclipse.emf.common 2.3
   */
  public String toPlatformString(boolean decode)
  {
    if (isPlatform())
    {
      StringBuilder result = new StringBuilder();
      for (int i = 1, len = segments.length; i < len; i++)
      {
        result.append('/').append(decode ? URI.decode(segments[i]) : segments[i]);
      }
      return result.toString();
    }
    return null;
  }

  /**
   * Returns the URI formed by appending the specified segment on to the end
   * of the path of this URI, if hierarchical; this URI unchanged,
   * otherwise.  If this URI has an authority and/or device, but no path,
   * the segment becomes the first under the root in an absolute path.
   *
   * @exception java.lang.IllegalArgumentException if <code>segment</code>
   * is not a valid segment according to {@link #validSegment}.
   */
  public URI appendSegment(String segment)
  {
    if (!validSegment(segment))
    {
      throw new IllegalArgumentException("invalid segment: " + segment);
    }

    if (!isHierarchical()) {
		return this;
	}

    boolean newAbsolutePath = !hasRelativePath();

    int len = segments.length;
    String[] newSegments = new String[len + 1];
    System.arraycopy(segments, 0, newSegments, 0, len);
    newSegments[len] = segment;

    return new URI(true, scheme, authority, device, newAbsolutePath,
                   newSegments, query, fragment);
  }

  /**
   * Returns the URI formed by appending the specified segments on to the
   * end of the path of this URI, if hierarchical; this URI unchanged,
   * otherwise.  If this URI has an authority and/or device, but no path,
   * the segments are made to form an absolute path.
   *
   * @param segments an array of non-null strings, each representing one
   * segment of the path.  If desired, a trailing separator should be
   * represented by an empty-string segment as the last element of the
   * array.
   *
   * @exception java.lang.IllegalArgumentException if <code>segments</code>
   * is not a valid segment array according to {@link #validSegments}.
   */
  public URI appendSegments(String[] segments)
  {
    if (!validSegments(segments))
    {
      String s = segments == null ? "invalid segments: null" :
        "invalid segment: " + firstInvalidSegment(segments);
      throw new IllegalArgumentException(s);
    }

    if (!isHierarchical()) {
		return this;
	}

    boolean newAbsolutePath = !hasRelativePath();

    int len = this.segments.length;
    int segmentsCount = segments.length;
    String[] newSegments = new String[len + segmentsCount];
    System.arraycopy(this.segments, 0, newSegments, 0, len);
    System.arraycopy(segments, 0, newSegments, len, segmentsCount);

    return new URI(true, scheme, authority, device, newAbsolutePath,
                   newSegments, query, fragment);
  }

  /**
   * Returns the URI formed by trimming the specified number of segments
   * (including empty segments, such as one representing a trailing
   * separator) from the end of the path of this URI, if hierarchical;
   * otherwise, this URI is returned unchanged.
   *
   * <p>Note that if all segments are trimmed from an absolute path, the
   * root absolute path remains.
   *
   * @param i the number of segments to be trimmed in the returned URI.  If
   * less than 1, this URI is returned unchanged; if equal to or greater
   * than the number of segments in this URI's path, all segments are
   * trimmed.
   */
  public URI trimSegments(int i)
  {
    if (!isHierarchical() || i < 1) {
		return this;
	}

    String[] newSegments = NO_SEGMENTS;
    int len = segments.length - i;
    if (len > 0)
    {
      newSegments = new String[len];
      System.arraycopy(segments, 0, newSegments, 0, len);
    }
    return new URI(true, scheme, authority, device, hasAbsolutePath(),
                   newSegments, query, fragment);
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI that has a path
   * that ends with a trailing separator; <code>false</code> otherwise.
   *
   * <p>A trailing separator is represented as an empty segment as the
   * last segment in the path; note that this definition does <em>not</em>
   * include the lone separator in the root absolute path.
   */
  public boolean hasTrailingPathSeparator()
  {
    return segments.length > 0 &&
      SEGMENT_EMPTY.equals(segments[segments.length - 1]);
  }

  /**
   * If this is a hierarchical URI whose path includes a file extension,
   * that file extension is returned; null otherwise.  We define a file
   * extension as any string following the last period (".") in the final
   * path segment.  If there is no path, the path ends in a trailing
   * separator, or the final segment contains no period, then we consider
   * there to be no file extension.  If the final segment ends in a period,
   * then the file extension is an empty string.
   */
  public String fileExtension()
  {
    int len = segments.length;
    if (len == 0) {
		return null;
