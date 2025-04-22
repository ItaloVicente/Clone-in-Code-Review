        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        copyAction.selectionChanged(selection);
        pasteAction.selectionChanged(selection);
        deleteAction.selectionChanged(selection);
        moveAction.selectionChanged(selection);
        renameAction.selectionChanged(selection);
    }
