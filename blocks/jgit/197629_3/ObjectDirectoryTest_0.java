	@Test
	public void testWindowCursorGetCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		WindowCursor curs = new WindowCursor(db.getObjectDatabase());
		assertNull(curs.getCommitGraph());
		commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		assertNotNull(curs.getCommitGraph());

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION

		assertNull(curs.getCommitGraph());
	}

