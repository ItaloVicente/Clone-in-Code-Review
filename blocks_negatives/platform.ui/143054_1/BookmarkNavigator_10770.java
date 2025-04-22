    }

    /**
     * Returns the shell.
     */
    Shell getShell() {
        return getViewSite().getShell();
    }

    /**
     * Returns the viewer used to display bookmarks.
     *
     * @return the viewer, or <code>null</code> if this view's controls
     *  have not been created yet
     */
    StructuredViewer getViewer() {
        return viewer;
    }

    /**
     * Returns the workspace.
     */
    IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Handles key events in viewer.
     */
    void handleKeyPressed(KeyEvent event) {
        if (event.character == SWT.DEL && event.stateMask == 0
                && removeAction.isEnabled()) {
