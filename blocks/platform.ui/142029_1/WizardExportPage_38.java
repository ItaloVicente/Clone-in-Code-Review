		return resourceNameField.getText();
	}

	protected IPath getResourcePath() {
		return getPathFromText(this.resourceNameField);
	}

	protected List getSelectedResources() {
		if (selectedResources == null) {
			IResource sourceResource = getSourceResource();

			if (sourceResource != null) {
