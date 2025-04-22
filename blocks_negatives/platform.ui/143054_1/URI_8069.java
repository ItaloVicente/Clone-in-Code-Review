    int i = authority.indexOf(PORT_SEPARATOR);
    return i < 0 ? null : authority.substring(i + 1);
  }

  /**
   * If this is a hierarchical URI with a device component, returns it;
   * <code>null</code> otherwise.
   */
  public String device()
  {
    return device;
  }

  /**
   * If this is a hierarchical URI with a path, returns an array containing
   * the segments of the path; an empty array otherwise.  The leading
   * separator in an absolute path is not represented in this array, but a
   * trailing separator is represented by an empty-string segment as the
   * final element.
   */
  public String[] segments()
  {
    return segments.clone();
  }

  /**
   * Returns an unmodifiable list containing the same segments as the array
   * returned by {@link #segments segments}.
   */
  public List<String> segmentsList()
  {
    return Collections.unmodifiableList(Arrays.asList(segments));
  }

  /**
   * Returns the number of elements in the segment array that would be
   * returned by {@link #segments segments}.
   */
  public int segmentCount()
  {
    return segments.length;
  }

  /**
   * Provides fast, indexed access to individual segments in the path
   * segment array.
   *
   * @exception java.lang.IndexOutOfBoundsException if <code>i < 0</code> or
   * <code>i >= segmentCount()</code>.
   */
  public String segment(int i)
  {
    return segments[i];
  }

  /**
   * Returns the last segment in the segment array, or <code>null</code>.
   */
  public String lastSegment()
  {
    int len = segments.length;
    if (len == 0) {
		return null;
