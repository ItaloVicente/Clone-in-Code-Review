		setPageComplete(determinePageCompletion());
		updateWidgetEnablements();
	}

	protected void handleResourceBrowseButtonPressed() {
		IResource currentFolder = getSourceResource();
		if (currentFolder != null && currentFolder.getType() == IResource.FILE) {
