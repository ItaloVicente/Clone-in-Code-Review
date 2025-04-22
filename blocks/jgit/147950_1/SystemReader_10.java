			return systemConfig;
		}

		protected FileBasedConfig createSystemConfig(Config parent
			if (StringUtils.isEmptyOrNull(getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
				File configFile = fs.getGitSystemConfig();
				if (configFile != null) {
					return new FileBasedConfig(parent
				}
			}
			return new FileBasedConfig(null
				@Override
				public void load() {
				}

				@Override
				public boolean isOutdated() {
					return false;
				}
			};
