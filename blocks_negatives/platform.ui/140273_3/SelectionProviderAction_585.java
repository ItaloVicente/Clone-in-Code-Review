    /**
     * The <code>SelectionProviderAction</code> implementation of this
     * <code>ISelectionChangedListener</code> method calls
     * <code>selectionChanged(IStructuredSelection)</code> if the selection is
     * a structured selection but <code>selectionChanged(ISelection)</code> if it is
     * not. Subclasses should override either of those methods method to react to
     * selection changes.
     */
    @Override
