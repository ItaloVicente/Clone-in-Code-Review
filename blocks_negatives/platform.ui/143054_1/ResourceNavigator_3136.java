        return getViewSite().getShell();
    }

    /**
     * Returns the message to show in the status line.
     *
     * @param selection the current selection
     * @return the status line message
     * @since 2.0
     */
    protected String getStatusLineMessage(IStructuredSelection selection) {
        if (selection.size() == 1) {
            Object o = selection.getFirstElement();
            if (o instanceof IResource) {
                return ((IResource) o).getFullPath().makeRelative().toString();
            }
            return ResourceNavigatorMessages.ResourceNavigator_oneItemSelected;
        }
        if (selection.size() > 1) {
            return NLS.bind(ResourceNavigatorMessages.ResourceNavigator_statusLine, String.valueOf(selection.size()));
        }
    }

    /**
     * Returns the name for the given element.
     * Used as the name for the current frame.
     */
    String getFrameName(Object element) {
        if (element instanceof IResource) {
