  /**
   * Creates a {@link PositionInfo} object out of the given positioning
   * string.
   *
   * <p>
   * <b>Examples for a positioning string:</b>
   * <ul>
   * <li><code>last</code> - place an element to the end of a list</li>
   * <li><code>first</code> - place an element to the beginning of a list</li>
   * <li><code>index:3</code> - place an element at the provided index 3 in a
   * list</li>
   * <li><code>before:org.eclipse.test.id</code> - place an element in a list
   * in front of the element with the ID "org.eclipse.test.id"</li>
   * <li><code>after:org.eclipse.test.id</code> - place an element in a list
   * after the element with the ID "org.eclipse.test.id"</li>
   * </ul>
   * </p>
   *
   * @param positionInfo
   *          the positioning string
   * @return a {@link PositionInfo} which holds all the data mentioned in the
   *         positioning string, or <code>null</code> if the positioning
   *         string doesn't hold a positioning information
   */
  public static PositionInfo parse(String positionInfo) {
    Position position = Position.find(positionInfo);
    if (position != null) {
      switch (position) {
        case FIRST:
          return PositionInfo.FIRST;
