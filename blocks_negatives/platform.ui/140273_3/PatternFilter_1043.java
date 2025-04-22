    /**
     * Answers whether the given element in the given viewer matches
     * the filter pattern.  This is a default implementation that will
     * show a leaf element in the tree based on whether the provided
     * filter text matches the text of the given element's text, or that
     * of it's children (if the element has any).
     *
     * Subclasses may override this method.
     *
     * @param viewer the tree viewer in which the element resides
     * @param element the element in the tree to check for a match
     *
     * @return true if the element matches the filter pattern
     */
    public boolean isElementVisible(Viewer viewer, Object element){
    	return isParentMatch(viewer, element) || isLeafMatch(viewer, element);
    }
