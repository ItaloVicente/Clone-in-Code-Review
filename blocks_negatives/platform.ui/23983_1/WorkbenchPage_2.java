    }

    /**
     * Implements IWorkbenchPage
     * 
     * @see org.eclipse.ui.IWorkbenchPage#addPropertyChangeListener(IPropertyChangeListener)
     * @since 2.0
     * @deprecated individual views should store a working set if needed and
     *             register a property change listener directly with the
     *             working set manager to receive notification when the view
     *             working set is removed.
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        propertyChangeListeners.add(listener);
    }

    /*
     * (non-Javadoc) Method declared on ISelectionListener.
     */
    public void addSelectionListener(ISelectionListener listener) {
