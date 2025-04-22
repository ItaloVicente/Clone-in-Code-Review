	}

	void deactivateCellEditor() {
		treeEditor.setEditor(null, null, columnToEdit);
		if (cellEditor != null) {
			cellEditor.deactivate();
			fireCellEditorDeactivated(cellEditor);
			cellEditor.removeListener(editorListener);
			cellEditor = null;
		}
		setErrorMessage(null);
	}

	private void entrySelectionChanged() {
		SelectionChangedEvent changeEvent = new SelectionChangedEvent(this, getSelection());
		fireSelectionChanged(changeEvent);
	}

	private TreeItem findItem(IPropertySheetEntry entry) {
		TreeItem[] items = tree.getItems();
		for (int i = 0; i < items.length; i++) {
			TreeItem item = items[i];
			TreeItem findItem = findItem(entry, item);
			if (findItem != null) {
