	private static Optional<String> readExternalToolFromGitConfig(
			Function<StoredConfig, String> readConfiguredExternalTool,
			Repository repository) {
		StoredConfig repoConfig = repository.getConfig();
		String externalTool = readConfiguredExternalTool.apply(repoConfig);
		return Optional.ofNullable(externalTool);
