    String lastSegment = segments[len - 1];
    int i = lastSegment.lastIndexOf(FILE_EXTENSION_SEPARATOR);
    return i < 0 ? null : lastSegment.substring(i + 1);
  }

  /**
   * Returns the URI formed by appending a period (".") followed by the
   * specified file extension to the last path segment of this URI, if it is
   * hierarchical with a non-empty path ending in a non-empty segment;
   * otherwise, this URI is returned unchanged.

   * <p>The extension is appended regardless of whether the segment already
   * contains an extension.
   *
   * @exception java.lang.IllegalArgumentException if
   * <code>fileExtension</code> is not a valid segment (portion) according
   * to {@link #validSegment}.
   */
  public URI appendFileExtension(String fileExtension)
  {
    if (!validSegment(fileExtension))
    {
      throw new IllegalArgumentException(
        "invalid segment portion: " + fileExtension);
    }

    int len = segments.length;
    if (len == 0) {
		return this;
	}

    String lastSegment = segments[len - 1];
    if (SEGMENT_EMPTY.equals(lastSegment)) {
		return this;
	}
    StringBuilder newLastSegment = new StringBuilder(lastSegment);
    newLastSegment.append(FILE_EXTENSION_SEPARATOR);
    newLastSegment.append(fileExtension);

    String[] newSegments = new String[len];
    System.arraycopy(segments, 0, newSegments, 0, len - 1);
    newSegments[len - 1] = newLastSegment.toString();

    return new URI(true, scheme, authority, device, hasAbsolutePath(),
                   newSegments, query, fragment);
  }

  /**
   * If this URI has a non-null {@link #fileExtension fileExtension},
   * returns the URI formed by removing it; this URI unchanged, otherwise.
   */
  public URI trimFileExtension()
  {
    int len = segments.length;
    if (len == 0) {
		return this;
	}

    String lastSegment = segments[len - 1];
    int i = lastSegment.lastIndexOf(FILE_EXTENSION_SEPARATOR);
    if (i < 0) {
		return this;
	}

    String newLastSegment = lastSegment.substring(0, i);
    String[] newSegments = new String[len];
    System.arraycopy(segments, 0, newSegments, 0, len - 1);
    newSegments[len - 1] = newLastSegment;

    return new URI(true, scheme, authority, device, hasAbsolutePath(),
                   newSegments, query, fragment);
  }

  /**
   * Returns <code>true</code> if this is a hierarchical URI that ends in a
   * slash; that is, it has a trailing path separator or is the root
   * absolute path, and has no query and no fragment; <code>false</code>
   * is returned otherwise.
   */
  public boolean isPrefix()
  {
    return isHierarchical() && query == null && fragment == null &&
      (hasTrailingPathSeparator() || (hasAbsolutePath() && segments.length == 0));
  }

  /**
   * If this is a hierarchical URI reference and <code>oldPrefix</code> is a
   * prefix of it, this returns the URI formed by replacing it by
   * <code>newPrefix</code>; <code>null</code> otherwise.
   *
   * <p>In order to be a prefix, the <code>oldPrefix</code>'s
   * {@link #isPrefix isPrefix} must return <code>true</code>, and it must
   * match this URI's scheme, authority, and device.  Also, the paths must
   * match, up to prefix's end.
   *
   * @exception java.lang.IllegalArgumentException if either
   * <code>oldPrefix</code> or <code>newPrefix</code> is not a prefix URI
   * according to {@link #isPrefix}.
   */
  public URI replacePrefix(URI oldPrefix, URI newPrefix)
  {
    if (!oldPrefix.isPrefix() || !newPrefix.isPrefix())
    {
      String which = oldPrefix.isPrefix() ? "new" : "old";
      throw new IllegalArgumentException("non-prefix " + which + " value");
    }

    String[] tailSegments = getTailSegments(oldPrefix);
    if (tailSegments == null) {
