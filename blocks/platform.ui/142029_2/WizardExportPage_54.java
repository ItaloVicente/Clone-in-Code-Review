	}

	protected void setResourceToDisplay(IResource resource) {
		setResourceFieldValue(resource.getFullPath().makeRelative().toString());
	}

	public void setTypesFieldValue(String value) {
		if (typesToExportField == null) {
