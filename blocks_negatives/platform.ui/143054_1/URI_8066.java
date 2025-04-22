    }
    return true;
  }

  private static boolean equals(Object o1, Object o2)
  {
    return o1 == null ? o2 == null : o1.equals(o2);
  }

  private static boolean equals(String s1, String s2, boolean ignoreCase)
  {
    return s1 == null ? s2 == null :
      ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
  }

  /**
   * If this is an absolute URI, returns the scheme component;
   * <code>null</code> otherwise.
   */
  public String scheme()
  {
    return scheme;
  }

  /**
   * If this is a non-hierarchical URI, returns the opaque part component;
   * <code>null</code> otherwise.
   */
  public String opaquePart()
  {
    return isHierarchical() ? null : authority;
  }

  /**
   * If this is a hierarchical URI with an authority component, returns it;
   * <code>null</code> otherwise.
   */
  public String authority()
  {
    return isHierarchical() ? authority : null;
  }

  /**
   * If this is a hierarchical URI with an authority component that has a
   * user info portion, returns it; <code>null</code> otherwise.
   */
  public String userInfo()
  {
    if (!hasAuthority()) {
		return null;
