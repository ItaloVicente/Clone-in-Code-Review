  /**
   * Creates an instance of the PositionInfo.
   *
   * @param position
   *          the kind of the positioning
   * @param positionReference
   *          additional information which is need to position an element
   *          (e.g.: index, ID of another element)
   * @throws NullPointerException
   *           if the <code>position</code> is <code>null</code>
   */
  public PositionInfo(Position position, String positionReference) {
    if (position == null) {
      throw new NullPointerException("No position given!");
    }
