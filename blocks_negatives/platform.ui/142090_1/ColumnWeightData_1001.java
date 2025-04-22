    /**
     * Creates a column width with the given weight and a default
     * minimum width.
     *
     * @param weight the weight of the column
     * @param resizable <code>true</code> if the column is resizable,
     *   and <code>false</code> if size of the column is fixed
     */
    public ColumnWeightData(int weight, boolean resizable) {
        this(weight, MINIMUM_WIDTH, resizable);
    }
