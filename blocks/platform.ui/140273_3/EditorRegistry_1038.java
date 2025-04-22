		if (!readEditors(editorTable)) {
			return false;
		}
		return readResources(editorTable);
	}

	private String mappingKeyFor(String type) {
