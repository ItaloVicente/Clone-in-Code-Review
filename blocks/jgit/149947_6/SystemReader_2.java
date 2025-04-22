	private void updateAll(Config config)
			throws IOException
		if (config == null) {
			return;
		}
		updateAll(config.getBaseConfig());
		if (config instanceof FileBasedConfig) {
			FileBasedConfig cfg = (FileBasedConfig) config;
			if (!cfg.getFile().exists()) {
				return;
			}
			if (cfg.isOutdated()) {
				LOG.debug("loading system config {}"
				cfg.load();
			}
		}
	}

