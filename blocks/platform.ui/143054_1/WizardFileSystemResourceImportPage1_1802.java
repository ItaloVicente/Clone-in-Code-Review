		createRootDirectoryGroup(parent);
		createFileSelectionGroup(parent);
		createButtonsGroup(parent);
	}

	protected void enableButtonGroup(boolean enable) {
		selectTypesButton.setEnabled(enable);
		selectAllButton.setEnabled(enable);
		deselectAllButton.setEnabled(enable);
	}

	protected boolean ensureSourceIsValid() {
		if (new File(getSourceDirectoryName()).isDirectory()) {
