
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null, FS.DETECTED);
		userConfig.setBoolean("http",
				"sslVerify", false);
		userConfig.setBoolean("http",
				"sslVerify", false);
		userConfig.save();
