  /**
   * Returns the additional information which is needed to place an element as
   * an int.
   *
   * @return the positionReference as an int
   * @throws NumberFormatException
   *           if the {@link #positionReference} can't be parsed to an int
   * @throws NullPointerException
   *           if the {@link #positionReference} is <code>null</code>
   */
  public int getPositionReferenceAsInteger() {
    return Integer.parseInt(positionReference);
  }
