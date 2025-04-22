	private static Optional<String> readDiffToolFromGitConfig(
			Repository repository) {
		StoredConfig repoConfig = repository.getConfig();
		String diffTool = getCustomDiffToolFromGitConfig(repoConfig);
		if (diffTool != null) {
			updateDefaultDiffToolPreference(diffTool);
		} else {
			FileBasedConfig config = loadUserConfig();
			updateDefaultDiffToolFromGitConfig(config);
		}
