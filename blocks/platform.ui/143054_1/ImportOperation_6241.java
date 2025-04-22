		}
		return true;
	}

	public void setContext(Shell shell) {
		context = shell;
	}

	public void setCreateContainerStructure(boolean value) {
		createContainerStructure = value;
	}

	public void setFilesToImport(List filesToImport) {
		this.selectedFiles = filesToImport;
	}

	public void setOverwriteResources(boolean value) {
		if (value) {
