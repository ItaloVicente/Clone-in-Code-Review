			return c;
		}

		protected FileBasedConfig createSystemConfig(Config parent, FS fs) {
			if (StringUtils.isEmptyOrNull(getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
				File configFile = fs.getGitSystemConfig();
				if (configFile != null) {
					return new FileBasedConfig(parent, configFile, fs);
				}
			}
			return new FileBasedConfig(null, fs) {
				@Override
				public void load() {
				}

				@Override
				public boolean isOutdated() {
					return false;
				}
			};
