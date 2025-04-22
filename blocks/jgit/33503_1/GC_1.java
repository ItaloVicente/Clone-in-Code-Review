		FileBasedConfig repoConfig = repo.getConfig();
		PackConfig config = new PackConfig(repo);
		if (aggressive) {
			config.setDeltaSearchWindowSize(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_WINDOW
					DEFAULT_GC_AGGRESSIVE_WINDOW));
			config.setMaxDeltaDepth(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_DEPTH
					DEFAULT_GC_AGGRESSIVE_DEPTH));
			config.setReuseObjects(false);
		}
		PackWriter pw = new PackWriter(config
