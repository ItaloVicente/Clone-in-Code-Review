    /**
     * Check if the parent (category) is a match to the filter text.  The default
     * behavior returns true if the element has at least one child element that is
     * a match with the filter text.
     *
     * Subclasses may override this method.
     *
     * @param viewer the viewer that contains the element
     * @param element the tree element to check
     * @return true if the given element has children that matches the filter text
     */
    protected boolean isParentMatch(Viewer viewer, Object element){
