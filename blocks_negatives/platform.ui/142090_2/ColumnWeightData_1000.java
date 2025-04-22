    /**
     * Creates a column width with the given weight and minimum width.
     *
     * @param weight the weight of the column
     * @param minimumWidth the minimum width of the column in pixels
     * @param resizable <code>true</code> if the column is resizable,
     *   and <code>false</code> if size of the column is fixed
     */
    public ColumnWeightData(int weight, int minimumWidth, boolean resizable) {
        super(resizable);
        Assert.isTrue(weight >= 0);
        Assert.isTrue(minimumWidth >= 0);
        this.weight = weight;
        this.minimumWidth = minimumWidth;
    }
