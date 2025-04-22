		File configFile = File.createTempFile("gitconfigtest", "config");
		MockSystemReader mockSystemReader = new MockSystemReader() {
			@Override
			public FileBasedConfig openUserConfig(Config parent, FS fs) {
				return new FileBasedConfig(parent, configFile, fs);
			}
		};
		configFile.deleteOnExit();
