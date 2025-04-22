    }

    /**
     * Handles a double-click event from the viewer.
     * Expands or collapses a folder when double-clicked.
     *
     * @param event the double-click event
     * @since 2.0
     */
    protected void handleDoubleClick(DoubleClickEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
                .getSelection();
        Object element = selection.getFirstElement();

        TreeViewer viewer = getTreeViewer();
        if (viewer.isExpandable(element)) {
            viewer.setExpandedState(element, !viewer.getExpandedState(element));
