	@Test(expected = JGitInternalException.class)
	public void testUnknownContentRequired() throws Exception {
		StoredConfig cfg = tdb.getRepository().getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_KEY_REQUIRED
		cfg.save();

		git.checkout().setName("test").call();
	}

