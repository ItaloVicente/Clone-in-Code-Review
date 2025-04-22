    /**
     * Returns whether expansion is possible for the current selection.  This
     * will only be true if it has children.
     *
     * @param element the object to test for expansion
     * @return <code>true</code> if expansion is possible; otherwise
     *		return <code>false</code
     */
    public boolean canExpand(Object element) {
        return fChildTree.isExpandable(element);
    }
