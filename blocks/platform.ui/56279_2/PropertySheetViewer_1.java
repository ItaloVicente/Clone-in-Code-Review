		cellEditor.addListener(editorListener);

		CellEditor.LayoutData layout = cellEditor.getLayoutData();
		treeEditor.horizontalAlignment = layout.horizontalAlignment;
		treeEditor.grabHorizontal = layout.grabHorizontal;
		treeEditor.minimumWidth = layout.minimumWidth;
		treeEditor.setEditor(control, item, columnToEdit);

		setErrorMessage(cellEditor.getErrorMessage());

		cellEditor.setFocus();

		fireCellEditorActivated(cellEditor);
	}

	void addActivationListener(ICellEditorActivationListener listener) {
		activationListeners.add(listener);
	}

	private void addColumns() {

		TreeColumn[] columns = tree.getColumns();
		for (int i = 0; i < columnLabels.length; i++) {
			String string = columnLabels[i];
			if (string != null) {
				TreeColumn column;
				if (i < columns.length) {
