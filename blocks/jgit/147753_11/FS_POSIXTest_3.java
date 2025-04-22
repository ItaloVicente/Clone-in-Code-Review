		tmp = Files.createTempDirectory("jgit_test_");
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);

		FS.getFileStoreAttributes(tmp.getParent());
		systemConfig = new FileBasedConfig(
				new File(tmp.toFile()
		userConfig = new FileBasedConfig(systemConfig
				new File(tmp.toFile()
		userConfig.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTODETACH
		userConfig.save();
		mockSystemReader.setSystemGitConfig(systemConfig);
		mockSystemReader.setUserGitConfig(userConfig);
