	@Test
	public void testCoreCommitGraphConfig() {
		Config config = new Config();
		assertFalse(config.get(CoreConfig.KEY).enableCommitGraph());

		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		assertTrue(config.get(CoreConfig.KEY).enableCommitGraph());

		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		assertFalse(config.get(CoreConfig.KEY).enableCommitGraph());
	}

