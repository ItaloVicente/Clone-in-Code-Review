    protected Object getSelectedObject() {
        ISelection selection = viewer.getSelection();
        if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
            IStructuredSelection structured = (IStructuredSelection) selection;
            return structured.getFirstElement();
        }
        return null;
    }

    /**
     * @return the viewer to which this drop support has been added.
     */
    protected Viewer getViewer() {
        return viewer;
    }

    /**
     * @deprecated this method should not be used. Exception handling has been
     * 	removed from DropTargetAdapter methods overridden by this class.
     * Handles any exception that occurs during callback, including
     * rethrowing behavior.
     * <p>
     * [Issue: Implementation prints stack trace and eats exception to avoid
     *  crashing VA/J.
     *  Consider conditionalizing the implementation to do one thing in VAJ
     *  and something more reasonable in other operating environments.
     * ]
     * </p>
     *
     * @param exception the exception
     * @param event the event
     */
    @Deprecated
