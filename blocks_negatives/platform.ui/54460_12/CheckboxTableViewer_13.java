    /**
     * Sets to the given value the grayed state for all elements in this viewer.
     *
     * @param state <code>true</code> if the element should be grayed,
     *  and <code>false</code> if it should be ungrayed
     */
    public void setAllGrayed(boolean state) {
        TableItem[] children = getTable().getItems();
        for (int i = 0; i < children.length; i++) {
            TableItem item = children[i];
