	protected boolean isExistingProject(File element) {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IPath location = project.getLocation();
			if (location != null && element.equals(location.toFile())) {
				return true;
			}
		}
		return false;
	}

