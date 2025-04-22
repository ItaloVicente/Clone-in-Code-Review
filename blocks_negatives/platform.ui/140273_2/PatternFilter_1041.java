    /**
     * Check if the current (leaf) element is a match with the filter text.
     * The default behavior checks that the label of the element is a match.
     *
     * Subclasses should override this method.
     *
     * @param viewer the viewer that contains the element
     * @param element the tree element to check
     * @return true if the given element's label matches the filter text
     */
    protected boolean isLeafMatch(Viewer viewer, Object element){
		String labelText = ((ILabelProvider) ((ContentViewer) viewer)
                .getLabelProvider()).getText(element);
