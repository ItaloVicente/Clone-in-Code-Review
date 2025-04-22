	private static Optional<String> readExternalToolFromGitConfig(
			Function<StoredConfig, String> readConfiguredExternalTool,
			Repository repository, String preferenceName) {
		StoredConfig repoConfig = repository.getConfig();
		String externalTool = readConfiguredExternalTool.apply(repoConfig);
		if (externalTool != null) {
			updateDefaultExternalToolPreference(externalTool, preferenceName);
		}
		return Optional.ofNullable(getStore().getString(preferenceName));
