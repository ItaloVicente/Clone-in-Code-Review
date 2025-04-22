    /**
     * Notifies this listener that the selection has changed.
     * <p>
     * This method is called when the selection changes from one to a
     * <code>non-null</code> value, but not when the selection changes to
     * <code>null</code>. If there is a requirement to be notified in the latter
     * scenario, implement <code>INullSelectionListener</code>. The event will
     * be posted through this method.
     * </p>
     *
     * @param part the workbench part containing the selection
     * @param selection the current selection. This may be <code>null</code>
     * 		if <code>INullSelectionListener</code> is implemented.
     */
    void selectionChanged(IWorkbenchPart part, ISelection selection);
