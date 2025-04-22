    /**
     * Notifies this listener that the given part has been opened.
     * <p>
     * Note that if other perspectives in the same page share the view,
     * this notification is not sent.  It is only sent when the view
     * is being newly opened in the page (it is being created).
     * </p>
     *
     * @param partRef the part that was opened
     * @see IWorkbenchPage#showView
     */
    public void partOpened(IWorkbenchPartReference partRef);
