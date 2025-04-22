    /**
     * Construct and return a growing row with custom properties
     *
     * @param size relative size of this row with respect to other growing rows
     * @param largerThanChildren true iff the preferred size of this row should
     * be based on the preferred sizes of its children
     * @return a new Row
     */
    public static Row growing(int size, boolean largerThanChildren) {
        return new Row(size, largerThanChildren);
    }
