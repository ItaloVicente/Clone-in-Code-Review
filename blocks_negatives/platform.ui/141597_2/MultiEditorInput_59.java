		int hash = 0;
		for (String editor : editors) {
			hash = hash * 37 + editor.hashCode();
		}
		for (IEditorInput editorInput : input) {
			hash = hash * 37 + editorInput.hashCode();
		}
		return hash;
