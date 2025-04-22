		updateAll(c);
		return c;
	}

	public StoredConfig getJGitConfig()
			throws ConfigInvalidException
		FileBasedConfig c = jgitConfig.get();
		if (c == null) {
			jgitConfig.compareAndSet(null
					openJGitConfig(null
			c = jgitConfig.get();
