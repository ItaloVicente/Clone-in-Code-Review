  /**
   * If this is a hierarchical URI with an authority component that has a
   * host portion, returns it; <code>null</code> otherwise.
   */
  public String host()
  {
    if (!hasAuthority()) {
		return null;
