    }

    /**
     * Returns the current sorter.
     * @since 2.0
     */
    public ResourceSorter getSorter() {
        return (ResourceSorter) getViewer().getSorter();
    }

    /**
     * Returns the tree viewer which shows the resource hierarchy.
     * @since 2.0
     */
    public TreeViewer getViewer() {
        return viewer;
    }

    /**
     * Returns the shell to use for opening dialogs.
     * Used in this class, and in the actions.
     */
    public Shell getShell() {
        return getViewSite().getShell();
    }

    /**
     * Returns the message to show in the status line.
     *
     * @param selection the current selection
     * @return the status line message
     */
    String getStatusLineMessage(IStructuredSelection selection) {
        if (selection.size() == 1) {
            Object o = selection.getFirstElement();
            if (o instanceof IResource) {
                return ((IResource) o).getFullPath().makeRelative().toString();
            }
