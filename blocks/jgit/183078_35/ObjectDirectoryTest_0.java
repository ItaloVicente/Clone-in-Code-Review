	@Test
	public void testGetCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		ObjectDirectory dir = db.getObjectDatabase();
		assertNull(dir.getCommitGraph());
		commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		assertNotNull(dir.getCommitGraph());
	}

