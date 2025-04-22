	private void verifyStaging(String projectName, String filePath,
			boolean expected) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		IResource resource = project.findMember(filePath);
		assertNotNull(filePath + " should exist", resource);
		IResourceState state = ResourceStateFactory.getInstance().get(resource);
		if (expected) {
			assertTrue(projectName + '/' + filePath + " should be staged",
					state.isStaged());
		} else {
			assertFalse(projectName + '/' + filePath + " should be unstaged",
					state.isStaged());
		}
	}
