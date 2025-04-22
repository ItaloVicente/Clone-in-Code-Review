
		if (effectiveConfig instanceof FileBasedConfig) {
			File configFile = ((FileBasedConfig) effectiveConfig).getFile();
			repositoryConfig = new FileBasedConfig(configFile, rep.getFS());
		} else {
			repositoryConfig = effectiveConfig;
		}
