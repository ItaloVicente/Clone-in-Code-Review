		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject testProject = workspace.getRoot().getProject(ObjectContributionClasses.PROJECT_NAME);
		if(! testProject.exists()) {
			testProject.create(null);
		}
		if(! testProject.isOpen()) {
			testProject.open(null);
		}
