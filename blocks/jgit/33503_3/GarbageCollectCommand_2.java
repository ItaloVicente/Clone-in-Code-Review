	public GarbageCollectCommand setAggressive(boolean aggressive) {
		if (aggressive) {
			StoredConfig repoConfig = repo.getConfig();
			pconfig.setDeltaSearchWindowSize(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_WINDOW
					DEFAULT_GC_AGGRESSIVE_WINDOW));
			pconfig.setMaxDeltaDepth(repoConfig.getInt(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_AGGRESSIVE_DEPTH
					DEFAULT_GC_AGGRESSIVE_DEPTH));
			pconfig.setReuseObjects(false);
		} else
			pconfig = new PackConfig(repo);
		return this;
	}

