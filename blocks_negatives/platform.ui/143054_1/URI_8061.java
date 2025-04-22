    for (int i = 0, len = value.length; i < len; i++)
    {
      if (!validSegment(value[i])) {
		return value[i];
	}
    }
    return null;
  }

  /**
   * Returns <code>true</code> if the specified <code>value</code> would be
   * valid as the query component of a URI; <code>false</code> otherwise.
   *
   * <p>A valid query may be null or contain any characters except for
   * <code>#</code>
   */
  public static boolean validQuery(String value)
  {
    return value == null || value.indexOf(FRAGMENT_SEPARATOR) == -1;


