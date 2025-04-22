	public RepositoryConfig getConfig() {
		if (userConfig.isOutdated()) {
			try {
				loadUserConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (config.isOutdated()) {
				try {
					loadConfig();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return config;
	}
