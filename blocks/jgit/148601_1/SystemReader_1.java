	public StoredConfig getUserConfig()
			throws IOException
		FileBasedConfig c = userConfig.get();
		if (c == null) {
			userConfig.compareAndSet(null
					openUserConfig(getSystemConfig()
			c = userConfig.get();
		} else {
			getSystemConfig();
		}
		if (c.isOutdated()) {
			LOG.debug("loading user config {}"
			c.load();
		}
		return c;
	}
