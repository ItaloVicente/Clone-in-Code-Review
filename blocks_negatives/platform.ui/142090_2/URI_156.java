  /**
   * If this is a hierarchical URI with an authority component that has a
   * port portion, returns it; <code>null</code> otherwise.
   */
  public String port()
  {
    if (!hasAuthority()) {
		return null;
