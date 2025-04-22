		if (StringUtils.isEmptyOrNull(SystemReader.getInstance().getenv(
				Constants.GIT_CONFIG_NOSYSTEM_KEY)))
			systemConfig = SystemReader.getInstance().openSystemConfig(null,
					getFS());
		else
			systemConfig = new FileBasedConfig(null, FS.DETECTED) {
				public void load() {
				}
