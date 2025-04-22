    /**
     * Sets to the given value the grayed state for all elements in this viewer.
     *
     * @param state <code>true</code> if the element should be grayed,
     *  and <code>false</code> if it should be ungrayed
     */
    public void setAllGrayed(boolean state) {
        TableItem[] children = getTable().getItems();
        for (TableItem item : children) {
            if (item.getData() != null) {
