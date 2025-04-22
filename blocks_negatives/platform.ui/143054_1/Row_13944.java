    /**
     * Creates a growing row.
     *
     * @param sizeRatio determines the size of this row with respect to other growing rows
     * (for example, a row with size = 3 will be 3x as large as a row with size = 1)
     * @param largerThanChildren true iff the preferred size of this row should take into
     * account the preferred sizes of its children.
     */
    public Row(int size, boolean largerThanChildren) {
        this.grows = true;
        this.size = size;
        this.largerThanChildren = largerThanChildren;
    }
