    int len = value.length();
    return len > 0 && value.charAt(len - 1) == DEVICE_IDENTIFIER &&
      !contains(value, SEGMENT_END_HI, SEGMENT_END_LO);
  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * a valid path segment of a URI; <code>false</code> otherwise.
   *
   * <p>A valid path segment must be non-null and not contain any of the
   * following characters: <code>/ ? #</code>
   */
  public static boolean validSegment(String value)
  {
    return value != null && !contains(value, SEGMENT_END_HI, SEGMENT_END_LO);


  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * a valid path segment array of a URI; <code>false</code> otherwise.
   *
   * <p>A valid path segment array must be non-null and contain only path
   * segments that are valid according to {@link #validSegment validSegment}.
   */
  public static boolean validSegments(String[] value)
  {
    if (value == null) {
		return false;
