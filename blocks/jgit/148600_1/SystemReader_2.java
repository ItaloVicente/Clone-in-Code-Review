	public StoredConfig getSystemConfig()
			throws IOException
		FileBasedConfig c = systemConfig.get();
		if (c == null) {
			systemConfig.compareAndSet(null
					openSystemConfig(null
			c = systemConfig.get();
		}
		if (c.isOutdated()) {
			LOG.debug("loading system config {}"
			c.load();
		}
		return c;
	}
