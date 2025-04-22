    return segments[len - 1];
  }

  /**
   * If this is a hierarchical URI with a path, returns a string
   * representation of the path; <code>null</code> otherwise.  The path
   * consists of a leading segment separator character (a slash), if the
   * path is absolute, followed by the slash-separated path segments.  If
   * this URI has a separate <a href="#device_explanation">device
   * component</a>, it is <em>not</em> included in the path.
   */
  public String path()
  {
    if (!hasPath()) {
		return null;
