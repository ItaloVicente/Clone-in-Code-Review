	@Test
	public void testLsRemoteWithoutLocalRepositoryUrlInsteadOf()
			throws Exception {
		String uri = fileUri();
		StoredConfig userConfig = SystemReader.getInstance().getUserConfig();
		userConfig.load();
		userConfig.setString("url"
		userConfig.save();
				.setHeads(true).call();
		assertNotNull(refs);
		assertEquals(2
	}

