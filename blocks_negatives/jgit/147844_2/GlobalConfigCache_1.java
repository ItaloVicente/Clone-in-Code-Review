	public static GlobalConfigCache setInstance(FileBasedConfig systemConfig,
			FileBasedConfig userConfig) {
		INSTANCE = new GlobalConfigCache(systemConfig, userConfig);
		return INSTANCE;
	}

	private FileBasedConfig userConfig;

	private FileBasedConfig systemConfig;

	private GlobalConfigCache() {
