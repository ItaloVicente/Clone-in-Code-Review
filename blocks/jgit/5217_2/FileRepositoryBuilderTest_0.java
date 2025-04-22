
	@Test
	public void emptyRepositoryFormatVersion() throws Exception {
		FileRepository r = createWorkRepository();
		FileBasedConfig config = r.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		new FileRepository(r.getDirectory());
	}

	@Test
	public void invalidRepositoryFormatVersion() throws Exception {
		FileRepository r = createWorkRepository();
		FileBasedConfig config = r.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		try {
			new FileRepository(r.getDirectory());
			fail("IllegalArgumentException not thrown");
		} catch (IllegalArgumentException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void unknownRepositoryFormatVersion() throws Exception {
		FileRepository r = createWorkRepository();
		FileBasedConfig config = r.getConfig();
		config.setLong(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		try {
			new FileRepository(r.getDirectory());
			fail("IOException not thrown");
		} catch (IOException e) {
			assertNotNull(e.getMessage());
		}
	}
