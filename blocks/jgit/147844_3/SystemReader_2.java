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
				try {
					LOG.debug("loading system config {}"
							systemConfig);
					systemConfig.load();
				} catch (ConfigInvalidException e) {
					LOG.error(
							MessageFormat.format(
									JGitText.get().systemConfigFileInvalid
									systemConfig.getFile().getAbsolutePath())
							e);
					throw e;
				}
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
				try {
					LOG.debug("loading user config {}"
					userConfig.load();
				} catch (ConfigInvalidException e) {
					LOG.error(MessageFormat.format(
							JGitText.get().userConfigFileInvalid
							userConfig.getFile().getAbsolutePath())
					throw e;
				}
			}
			return userConfig;
