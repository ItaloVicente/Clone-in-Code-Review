			if (userConfig == null) {
				File home = fs.userHome();
				userConfig = new FileBasedConfig(parent
						new File(home
			}
			return userConfig;
		}

		@Override
		public StoredConfig getSystemConfig()
				throws IOException
			if (systemConfig == null) {
				systemConfig = createSystemConfig(null
			}
			if (systemConfig.isOutdated()) {
				LOG.debug("loading system config {}"
				systemConfig.load();
			}
			return systemConfig;
		}

		@Override
		public StoredConfig getUserConfig()
				throws IOException
			if (userConfig == null) {
				userConfig = openUserConfig(getSystemConfig()
			} else {
				getSystemConfig();
			}
			if (userConfig.isOutdated()) {
				LOG.debug("loading user config {}"
				userConfig.load();
			}
			return userConfig;
