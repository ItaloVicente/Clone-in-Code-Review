		commit(projectName);
	}

	private void deleteFileAndCommit(String projectName) throws Exception {
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt")).delete(true, null);

		commit(projectName);
	}

	private void commit(String projectName) throws InterruptedException {
