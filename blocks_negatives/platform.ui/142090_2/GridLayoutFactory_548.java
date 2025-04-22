    /**
     * Sets whether the columns should be forced to be equal width
     *
     * @param equal true iff the columns should be forced to be equal width
     * @return this
     */
    public GridLayoutFactory equalWidth(boolean equal) {
        l.makeColumnsEqualWidth = equal;
        return this;
    }
