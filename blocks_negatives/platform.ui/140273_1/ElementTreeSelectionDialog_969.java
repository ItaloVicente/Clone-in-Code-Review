     * Returns the tree viewer.
     *
     * @return the tree viewer
     */
    protected TreeViewer getTreeViewer() {
        return fViewer;
    }

    private boolean evaluateIfTreeEmpty(Object input) {
        Object[] elements = fContentProvider.getElements(input);
        if (elements.length > 0) {
            if (fFilters != null) {
                for (int i = 0; i < fFilters.size(); i++) {
                    ViewerFilter curr = (ViewerFilter) fFilters.get(i);
                    elements = curr.filter(fViewer, input, elements);
                }
            }
        }
        return elements.length == 0;
    }

    /**
     * Set the result using the super class implementation of
     * buttonPressed.
     * @param id
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
     */
    protected void access$superButtonPressed(int id) {
        super.buttonPressed(id);
    }

    /**
     * Set the result using the super class implementation of
     * setResult.
     * @param result
     * @see SelectionStatusDialog#setResult(int, Object)
     */
    protected void access$setResult(List result) {
        super.setResult(result);
    }

    @Override
