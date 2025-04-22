		configFile = File.createTempFile("gitconfigtest", "config");
		configFile.deleteOnExit();
		SystemReader.setInstance(new MockSystemReader() {
			@Override
			public FileBasedConfig openUserConfig(Config parent, FS fs) {
				return new FileBasedConfig(parent, configFile, fs);
			}
		});
