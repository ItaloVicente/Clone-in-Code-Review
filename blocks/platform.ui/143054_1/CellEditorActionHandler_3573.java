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

	public CellEditorActionHandler(IActionBars actionBar) {
		super();
		actionBar.setGlobalActionHandler(ActionFactory.CUT.getId(), cellCutAction);
		actionBar.setGlobalActionHandler(ActionFactory.COPY.getId(), cellCopyAction);
		actionBar.setGlobalActionHandler(ActionFactory.PASTE.getId(), cellPasteAction);
		actionBar.setGlobalActionHandler(ActionFactory.DELETE.getId(), cellDeleteAction);
		actionBar.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), cellSelectAllAction);
		actionBar.setGlobalActionHandler(ActionFactory.FIND.getId(), cellFindAction);
		actionBar.setGlobalActionHandler(ActionFactory.UNDO.getId(), cellUndoAction);
		actionBar.setGlobalActionHandler(ActionFactory.REDO.getId(), cellRedoAction);
	}

	public void addCellEditor(CellEditor editor) {
		if (editor == null) {
			return;
