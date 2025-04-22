	public void editorChanged(IEditorPart part) {
		if (activeEditor != null) {
			activeEditor.removePropertyListener(this);
		}
		activeEditor = part;
		if (activeEditor != null) {
			activeEditor.addPropertyListener(this);
		}
		updateState();
	}
