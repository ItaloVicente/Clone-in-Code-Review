    }

    /**
     * Returns the number of rows or columns
     *
     * @param isRow if true, returns the number of rows. If false, returns the number of columns
     * @return
     */
    public int getNumRows(boolean isRow) {
        if (isRow) {
            return rows;
