	protected void createTestProject() throws CoreException {
		if (testProject == null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			testProject = workspace.getRoot().getProject("TestProject");
			testProject.create(null);
			testProject.open(null);
		}
	}
