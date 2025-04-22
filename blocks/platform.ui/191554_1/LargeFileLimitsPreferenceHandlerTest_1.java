	private void deleteTestFile() throws CoreException {
		boolean force = true;
		temporaryFile.delete(force, monitor);
		testProject.delete(force, monitor);
	}

