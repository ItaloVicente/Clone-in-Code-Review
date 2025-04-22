	@Test
	public void testSavedConfigFileShouldNotReadUserGitConfig()
			throws IOException {
		AtomicBoolean userConfigTimeRead = new AtomicBoolean(false);

		Path userConfigFile = createFile(CONTENT1.getBytes()
		mockSystemReader.setUserGitConfig(
				new FileBasedConfig(userConfigFile.toFile()

					@Override
					public long getTimeUnit(String section
							String name
						userConfigTimeRead.set(true);
						return super.getTimeUnit(section
								defaultValue
					}
				});

		Path file = createFile(CONTENT2.getBytes()
		FileBasedConfig fileBasedConfig = new FileBasedConfig(file.toFile()
				FS.DETECTED);
		fileBasedConfig.save();

		fileBasedConfig.isOutdated();
		assertFalse(
				"User config should not be read when accessing config files "
						+ "for avoiding deadlocks"
				userConfigTimeRead.get());
	}

