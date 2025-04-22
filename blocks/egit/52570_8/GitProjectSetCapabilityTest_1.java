		initNewTestDirectory();
	}

	private void initNewTestDirectory() throws Exception {
		testMethodNumber++;
		testDirectory = testUtils
				.createTempDir("LocalRepositoriesTests" + testMethodNumber);
		if (testDirectory.exists()) {
			FileUtils.delete(testDirectory,
					FileUtils.RECURSIVE | FileUtils.RETRY);
		}
		if (!testDirectory.exists()) {
			FileUtils.mkdir(testDirectory, true);
		}
		File repoRoot = new File(testDirectory, "RepositoryRoot");
		if (!repoRoot.exists()) {
			FileUtils.mkdir(repoRoot, true);
		}
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		p.put(GitCorePreferences.core_defaultRepositoryDir, repoRoot.getPath());
