		if (control.isFocusControl()) {
			activeEditor = editor;
			editor.addPropertyChangeListener(cellListener);
			updateActionsEnableState();
		}
	}

	public void dispose() {
		setCutAction(null);
		setCopyAction(null);
		setPasteAction(null);
		setDeleteAction(null);
		setSelectAllAction(null);
		setFindAction(null);
		setUndoAction(null);
		setRedoAction(null);

		Iterator itr = controlToEditor.keySet().iterator();
		while (itr.hasNext()) {
			Control control = (Control) itr.next();
			if (!control.isDisposed()) {
				control.removeListener(SWT.Activate, controlListener);
				control.removeListener(SWT.Deactivate, controlListener);
			}
		}
		controlToEditor.clear();
