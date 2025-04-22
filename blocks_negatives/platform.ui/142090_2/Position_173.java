  /**
   * Find the {@link Position} enum value used in the given positioning
   * string.
   *
   * @param positionInfo
   *          the positioning string (can be <code>null</code>, which would
   *          result in <code>null</code>)
   * @return the {@link Position} which is mentioned in the positioning
   *         string, or <code>null</code> if none can be found
   */
  public static final Position find(String positionInfo) {
    if (positionInfo == null || positionInfo.length() <= 0)
      return null;
