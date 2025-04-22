            if (activeEditor != null) {
                activeEditor.performRedo();
                return;
            }
            if (redoAction != null) {
                redoAction.runWithEvent(event);
                return;
            }
        }

        public void updateEnabledState() {
            if (activeEditor != null) {
                setEnabled(activeEditor.isRedoEnabled());
                setText(WorkbenchMessages.Workbench_redo);
                setToolTipText(WorkbenchMessages.Workbench_redoToolTip);
                return;
            }
            if (redoAction != null) {
                setEnabled(redoAction.isEnabled());
                setText(redoAction.getText());
                setToolTipText(redoAction.getToolTipText());
                return;
            }
            setEnabled(false);
        }
    }

    /**
     * Creates a <code>CellEditor</code> action handler
     * for the global Cut, Copy, Paste, Delete, Select All,
     * Find, Undo, and Redo of the action bar.
     *
     * @param actionBar the action bar to register global
     *    action handlers.
     */
    public CellEditorActionHandler(IActionBars actionBar) {
        super();
        actionBar.setGlobalActionHandler(ActionFactory.CUT.getId(),
                cellCutAction);
        actionBar.setGlobalActionHandler(ActionFactory.COPY.getId(),
                cellCopyAction);
        actionBar.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                cellPasteAction);
        actionBar.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                cellDeleteAction);
        actionBar.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                cellSelectAllAction);
        actionBar.setGlobalActionHandler(ActionFactory.FIND.getId(),
                cellFindAction);
        actionBar.setGlobalActionHandler(ActionFactory.UNDO.getId(),
                cellUndoAction);
        actionBar.setGlobalActionHandler(ActionFactory.REDO.getId(),
                cellRedoAction);
    }

    /**
     * Adds a <code>CellEditor</code> to the handler so that the
     * Cut, Copy, Paste, Delete, Select All, Find, Undo, and Redo
     * actions are redirected to it when active.
     *
     * @param editor the <code>CellEditor</code>
     */
    public void addCellEditor(CellEditor editor) {
        if (editor == null) {
			return;
