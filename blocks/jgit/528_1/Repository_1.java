		synchronized (configLock) {
			if (userConfig.isOutdated() || config.isOutdated())
				try {
					loadConfig();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			return config;
		}
