    StringBuilder result = new StringBuilder();
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
   * If this is a hierarchical URI with a path, returns a string
   * representation of the path, including the authority and the
   * <a href="#device_explanation">device component</a>;
   * <code>null</code> otherwise.
   *
   * <p>If there is no authority, the format of this string is:
   * <pre>
   *   device/pathSegment1/pathSegment2...</pre>
   *
   * <p>If there is an authority, it is:
   * <pre>
   *
   * <p>For an <a href="#archive_explanation">archive URI</a>, it's just:
   * <pre>
   *   authority/pathSegment1/pathSegment2...</pre>
   */
  public String devicePath()
  {
    if (!hasPath()) {
		return null;
