	@Test
	public void testSavedConfigFileShouldNotReadUserGitConfig()
			throws IOException  {
		AtomicBoolean userConfigTimeRead = new AtomicBoolean(false);

		final Path userConfigFile = createFile(CONTENT1.getBytes()
		mockSystemReader.setUserGitConfig(new FileBasedConfig(userConfigFile.toFile()

			@Override
			public long getTimeUnit(String section
									long defaultValue
				userConfigTimeRead.set(true);
				return super.getTimeUnit(section
			}
		});

		final Path file = createFile(CONTENT2.getBytes()
		FileBasedConfig fileBasedConfig = new FileBasedConfig(file.toFile()
		fileBasedConfig.save();

		Assert.assertFalse("User config should not be read when accessing config files for avoiding deadlocks"
				userConfigTimeRead.get());
	}

