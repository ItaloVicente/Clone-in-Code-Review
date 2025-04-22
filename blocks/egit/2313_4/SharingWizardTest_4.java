		new Eclipse().reset();
	}

	private void erase(String projectName) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		if (project.exists()) {
			project.close(null);
			project.delete(false, null);
		}
