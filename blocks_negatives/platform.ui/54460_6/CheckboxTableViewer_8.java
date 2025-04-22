    /**
     * Returns the grayed state of the given element.
     *
     * @param element the element
     * @return <code>true</code> if the element is grayed,
     *   and <code>false</code> if not grayed
     */
    public boolean getGrayed(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TableItem) {
            return ((TableItem) widget).getGrayed();
        }
        return false;
    }

    /**
     * Returns a list of elements corresponding to grayed nodes in this
     * viewer.
     * <p>
     * This method is typically used when preserving the interesting
     * state of a viewer; <code>setGrayedElements</code> is used during the restore.
     * </p>
     *
     * @return the array of grayed elements
     * @see #setGrayedElements
     */
