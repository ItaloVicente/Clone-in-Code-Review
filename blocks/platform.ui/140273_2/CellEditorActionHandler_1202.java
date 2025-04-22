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
		}

		Control control = editor.getControl();
		Assert.isNotNull(control);
		controlToEditor.put(control, editor);
		control.addListener(SWT.Activate, controlListener);
		control.addListener(SWT.Deactivate, controlListener);
