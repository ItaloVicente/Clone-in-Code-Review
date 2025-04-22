		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());
		setErrorMessage(null); // should not initially have error message

		setControl(composite);
	}

	protected abstract void createDestinationGroup(Composite parent);

	protected final void createResourcesGroup(Composite parent) {

		List input = new ArrayList();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (project.isOpen()) {
