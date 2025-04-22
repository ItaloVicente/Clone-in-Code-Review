	private void assertConnected(IProject project) {
		RepositoryProvider provider = RepositoryProvider.getProvider(project,
				GitProvider.ID);
		if (provider == null) {
			TestUtil.waitForJobs(100, 5000);
			provider = RepositoryProvider.getProvider(project, GitProvider.ID);
		}
		assertNotNull("GitProvider not mapped to: " + project, provider);

		GitProjectData data = ((GitProvider) provider).getData();
		if (data == null) {
			TestUtil.waitForJobs(100, 5000);
			data = ((GitProvider) provider).getData();
		}
		assertNotNull("GitProjectData is null for: " + project, data);

		RepositoryMapping mapping = data.getRepositoryMapping(project);
		if (mapping == null) {
			TestUtil.waitForJobs(100, 5000);
			mapping = data.getRepositoryMapping(project);
		}
		assertNotNull("RepositoryMapping is null for: " + project, mapping);
	}

