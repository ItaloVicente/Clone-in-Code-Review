    }

    /**
     * Handles a selection change.
     *
     * @param selection the new selection
     */
    void handleSelectionChanged(IStructuredSelection selection) {
        openAction.selectionChanged(selection);
        removeAction.selectionChanged(selection);
        editAction.selectionChanged(selection);
        selectAllAction.selectionChanged(selection);
        showInNavigatorAction.selectionChanged(selection);
    }

    @Override
