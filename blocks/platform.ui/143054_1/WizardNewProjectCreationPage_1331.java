	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
	}

	public String getProjectName() {
		if (projectNameField == null) {
