    /**
     * Construct and return a fixed-size row. The row will not grow when the layout
     * is resized, and its size will be computed from the default sizes of its children.
     *
     * @return a new Row
     */
    public static Row fixed() {
        return new Row(false);
    }
