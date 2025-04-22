		IEditorDescriptor desc = findEditor(editorId);
		setDefaultEditor(fileName, desc);
	}

	public void setDefaultEditor(String fileName, IEditorDescriptor desc) {
		FileEditorMapping[] mapping = getMappingForFilename(fileName);
		if (mapping[0] != null) {
