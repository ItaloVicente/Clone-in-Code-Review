		if (activeEditor != null) {
			activeEditor.removePropertyChangeListener(cellListener);
		}
		activeEditor = null;

	}

	public void removeCellEditor(CellEditor editor) {
		if (editor == null) {
