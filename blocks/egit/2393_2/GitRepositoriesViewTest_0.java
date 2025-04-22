	
	private void assertProjectIsShared(String projectName,
			boolean shouldBeShared) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (shouldBeShared) {
			assertNotNull(mapping);
			assertNotNull(mapping.getRepository());
		} else
			assertNull(mapping);
	}
