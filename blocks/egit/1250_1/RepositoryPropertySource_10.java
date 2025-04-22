		StoredConfig config;
		String category;
		String prefix;
		switch (getCurrentMode()) {
		case EFFECTIVE:
			prefix = EFFECTIVE_ID_PREFIX;
			category = UIText.RepositoryPropertySource_EffectiveConfigurationCategory;
			config = effectiveConfig;
			break;
		case REPO: {
			prefix = REPO_ID_PREFIX;
			String location = ""; //$NON-NLS-1$
			if (repositoryConfig instanceof FileBasedConfig) {
				location = ((FileBasedConfig) repositoryConfig).getFile()
						.getAbsolutePath();
