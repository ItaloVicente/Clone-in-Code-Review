
		File configFile = File.createTempFile("gitconfigtest", "config");
		MockSystemReader mockSystemReader = new MockSystemReader() {
			@Override
			public FileBasedConfig openUserConfig(Config parent, FS fs) {
				return new FileBasedConfig(parent, configFile, fs);
			}
		};
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_NAME_KEY, null);
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_EMAIL_KEY, null);
		mockSystemReader.setProperty(Constants.GIT_COMMITTER_NAME_KEY, null);
		mockSystemReader.setProperty(Constants.GIT_COMMITTER_EMAIL_KEY, null);
		configFile.deleteOnExit();
		SystemReader.setInstance(mockSystemReader);
		mockSystemReader.setProperty(Constants.GIT_CEILING_DIRECTORIES_KEY,
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile()
						.getParentFile().getAbsoluteFile().toString());
		FileBasedConfig userConfig = mockSystemReader.openUserConfig(null,
				FS.DETECTED);
		userConfig.setBoolean(ConfigConstants.CONFIG_GC_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTODETACH, false);
		userConfig.save();
