		return null;
	}

	IProject createExistingProject() {

		String projectName = projectNameField.getText();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		if (this.description == null) {
			this.description = workspace.newProjectDescription(projectName);
			IPath locationPath = getLocationPath();
			if (isPrefixOfRoot(locationPath)) {
