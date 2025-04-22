    /**
     * Notifies this listener that the given part has been closed.
     * <p>
     * Note that if other perspectives in the same page share the view,
     * this notification is not sent.  It is only sent when the view
     * is being removed from the page entirely (it is being disposed).
     * </p>
     *
     * @param partRef the part that was closed
     * @see IWorkbenchPage#hideView
     */
    void partClosed(IWorkbenchPartReference partRef);
