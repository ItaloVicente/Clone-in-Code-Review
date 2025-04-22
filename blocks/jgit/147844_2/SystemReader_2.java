	protected void setGlobalConfigCache(FileBasedConfig systemConfig
			FileBasedConfig userConfig) {
		this.globalConfigCache = new GlobalConfigCache(systemConfig
				userConfig);
	}

