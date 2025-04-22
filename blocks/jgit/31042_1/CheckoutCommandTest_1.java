	public void testCheckoutRemoteTrackingWithUpstream() throws Exception {
		Repository db2 = createRepositoryWithRemote();

		Git.wrap(db2).checkout().setCreateBranch(true).setName("test")
				.setStartPoint("origin/test")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();

		assertEquals("refs/heads/test"
				.getName());
		StoredConfig config = db2.getConfig();
		assertEquals("origin"
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REMOTE));
		assertEquals("refs/heads/test"
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE));
	}
