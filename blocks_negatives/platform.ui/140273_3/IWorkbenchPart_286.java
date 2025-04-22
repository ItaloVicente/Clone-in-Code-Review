    /**
     * Asks this part to take focus within the workbench. Parts must
     * assign focus to one of the controls contained in the part's
     * parent composite.
     * <p>
     * Clients should not call this method (the workbench calls this method at
     * appropriate times).  To have the workbench activate a part, use
     * <code>IWorkbenchPage.activate(IWorkbenchPart) instead</code>.
     * </p>
     */
    void setFocus();
