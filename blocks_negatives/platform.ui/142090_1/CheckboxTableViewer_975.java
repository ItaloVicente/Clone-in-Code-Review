        return false;
    }

    /**
     * Sets which nodes are checked in this viewer.
     * The given list contains the elements that are to be checked;
     * all other nodes are to be unchecked.
     * Does not fire events to check state listeners.
     * <p>
     * This method is typically used when restoring the interesting
     * state of a viewer captured by an earlier call to <code>getCheckedElements</code>.
     * </p>
     *
     * @param elements the list of checked elements (element type: <code>Object</code>)
     * @see #getCheckedElements
     */
    public void setCheckedElements(Object[] elements) {
        assertElementsNotNull(elements);
        CustomHashtable set = newHashtable(elements.length * 2 + 1);
        for (Object element : elements) {
            set.put(element, element);
        }
        TableItem[] items = getTable().getItems();
        for (TableItem item : items) {
            Object element = item.getData();
            if (element != null) {
                boolean check = set.containsKey(element);
                if (item.getChecked() != check) {
                    item.setChecked(check);
                }
            }
        }
    }

    /**
     * Sets the grayed state for the given element in this viewer.
     *
     * @param element the element
     * @param state <code>true</code> if the item should be grayed,
     *  and <code>false</code> if it should be ungrayed
     * @return <code>true</code> if the element is visible and the gray
     *  state could be set, and <code>false</code> otherwise
     */
    public boolean setGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = findItem(element);
