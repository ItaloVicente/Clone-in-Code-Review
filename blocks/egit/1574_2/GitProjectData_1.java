		return propertyFile(getProject());
	}

	private static File propertyFile(IProject project) {
		return new File(project.getWorkingLocation(Activator.getPluginId())
				.toFile(), "GitProjectData.properties"); //$NON-NLS-1$
