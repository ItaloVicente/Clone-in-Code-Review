	public StoredConfig getJGitConfig()
			throws IOException
		FileBasedConfig c = jgitConfig.get();
		if (c == null) {
			jgitConfig.compareAndSet(null
					openJGitConfig(null
			c = jgitConfig.get();
		}
		if (c.isOutdated()) {
			LOG.debug("loading jgit config {}"
			c.load();
		}
		return c;
	}

