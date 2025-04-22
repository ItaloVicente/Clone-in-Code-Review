        Object element = selection.getFirstElement();

        if (viewer.isExpandable(element)) {
            viewer.setExpandedState(element, !viewer.getExpandedState(element));
        }

    }

    /**
     * Handles selection changed in viewer.
     * Updates global actions.
     * Links to editor (if option enabled)
     * @since 2.0
     */
    protected void handleSelectionChanged(SelectionChangedEvent event) {
