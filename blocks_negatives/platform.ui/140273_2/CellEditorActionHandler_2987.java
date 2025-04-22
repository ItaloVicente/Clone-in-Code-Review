        cellUndoAction.updateEnabledState();
    }

    /**
     * Updates the enable state of the Cut, Copy,
     * Paste, Delete, Select All, Find, Undo, and
     * Redo action handlers
     */
    private void updateActionsEnableState() {
        cellCutAction.updateEnabledState();
        cellCopyAction.updateEnabledState();
        cellPasteAction.updateEnabledState();
        cellDeleteAction.updateEnabledState();
        cellSelectAllAction.updateEnabledState();
        cellFindAction.updateEnabledState();
        cellUndoAction.updateEnabledState();
        cellRedoAction.updateEnabledState();
    }
