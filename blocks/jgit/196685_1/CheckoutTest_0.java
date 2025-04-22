	@Test
	void testUnknownContentRequired() throws Exception {
		assertThrows(JGitInternalException.class
			StoredConfig cfg = tdb.getRepository().getConfig();
			cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
					ConfigConstants.CONFIG_SECTION_LFS
					ConfigConstants.CONFIG_KEY_REQUIRED
			cfg.save();

			git.checkout().setName("test").call();
		});
