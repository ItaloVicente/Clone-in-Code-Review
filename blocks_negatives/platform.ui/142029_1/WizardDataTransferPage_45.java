        return (new Path(text)).makeAbsolute();
    }

    /**
     * Queries the user to supply a container resource.
     *
     * @return the path to an existing or new container, or <code>null</code> if the
     *    user cancelled the dialog
     */
    protected IPath queryForContainer(IContainer initialSelection, String msg) {
        return queryForContainer(initialSelection, msg, null);
    }

    /**
     * Queries the user to supply a container resource.
     *
     * @return the path to an existing or new container, or <code>null</code> if the
     *    user cancelled the dialog
     */
    protected IPath queryForContainer(IContainer initialSelection, String msg,
            String title) {
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(
                getControl().getShell(), initialSelection,
                allowNewContainerName(), msg);
        if (title != null) {
