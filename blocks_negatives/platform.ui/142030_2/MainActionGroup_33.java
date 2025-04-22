        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();
        propertyDialogAction.setEnabled(selection.size() == 1);
        addBookmarkAction.selectionChanged(selection);
        addTaskAction.selectionChanged(selection);

        gotoGroup.updateActionBars();
        openGroup.updateActionBars();
        refactorGroup.updateActionBars();
        workingSetGroup.updateActionBars();
        sortAndFilterGroup.updateActionBars();
        workspaceGroup.updateActionBars();
        undoRedoGroup.updateActionBars();
    }

    /**
     * Runs the default action (open file) by delegating the open group.
     */
    @Override
