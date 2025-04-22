    /**
     * Asks this part to take focus within the workbench.
     * <p>
     * Clients should not call this method (the workbench calls this method at
     * appropriate times).  To have the workbench activate a part, use
     * {@link IIntroManager#showIntro(IWorkbenchWindow, boolean)}.
     * </p>
     */
    void setFocus();
