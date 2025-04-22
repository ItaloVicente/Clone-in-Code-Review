			FileBasedConfig c = systemConfig.get();
			if (c == null) {
				systemConfig.compareAndSet(null,
						createSystemConfig(parent, fs));
				c = systemConfig.get();
			}
			return c;
		}

		protected FileBasedConfig createSystemConfig(Config parent, FS fs) {
			if (StringUtils.isEmptyOrNull(getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
