	@Test(expected = JGitInternalException.class)
	public void testUnknownContentRequired() throws Exception {
		StoredConfig cfg = tdb.getRepository().getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION,
				ConfigConstants.CONFIG_SECTION_LFS,
				ConfigConstants.CONFIG_KEY_REQUIRED, true);
		cfg.save();

		git.checkout().setName("test").call();
