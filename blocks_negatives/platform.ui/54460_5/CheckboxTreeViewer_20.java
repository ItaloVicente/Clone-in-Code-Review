        return false;
    }

    /**
     * Returns a list of checked elements in this viewer's tree,
     * including currently hidden ones that are marked as
     * checked but are under a collapsed ancestor.
     * <p>
     * This method is typically used when preserving the interesting
     * state of a viewer; <code>setCheckedElements</code> is used during the restore.
     * </p>
     *
     * @return the array of checked elements
     *
     * @see #setCheckedElements
     */
    public Object[] getCheckedElements() {
        ArrayList v = new ArrayList();
        Control tree = getControl();
        internalCollectChecked(v, tree);
        return v.toArray();
    }

    /**
     * Returns the grayed state of the given element.
     *
     * @param element the element
     * @return <code>true</code> if the element is grayed,
     *   and <code>false</code> if not grayed
     */
    public boolean getGrayed(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
            return ((TreeItem) widget).getGrayed();
        }
        return false;
    }

    /**
     * Returns a list of grayed elements in this viewer's tree,
     * including currently hidden ones that are marked as
     * grayed but are under a collapsed ancestor.
     * <p>
     * This method is typically used when preserving the interesting
     * state of a viewer; <code>setGrayedElements</code> is used during the restore.
     * </p>
     *
     * @return the array of grayed elements
     *
     * @see #setGrayedElements
     */
    public Object[] getGrayedElements() {
        List result = new ArrayList();
        internalCollectGrayed(result, getControl());
        return result.toArray();
    }

    @Override
