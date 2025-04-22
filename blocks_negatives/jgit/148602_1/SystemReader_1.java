			FileBasedConfig c = userConfig.get();
			if (c == null) {
				userConfig.compareAndSet(null, new FileBasedConfig(parent,
						new File(fs.userHome(), ".gitconfig"), fs)); //$NON-NLS-1$
				c = userConfig.get();
			}
			return c;
		}

		@Override
		public StoredConfig getSystemConfig()
				throws IOException, ConfigInvalidException {
			FileBasedConfig c = openSystemConfig(null, FS.DETECTED);
			if (c.isOutdated()) {
				LOG.debug("loading system config {}", systemConfig); //$NON-NLS-1$
				c.load();
			}
			return c;
		}

		@Override
		public StoredConfig getUserConfig()
				throws IOException, ConfigInvalidException {
			FileBasedConfig c = openUserConfig(getSystemConfig(), FS.DETECTED);
			if (c.isOutdated()) {
				LOG.debug("loading user config {}", userConfig); //$NON-NLS-1$
				c.load();
			}
			return c;
