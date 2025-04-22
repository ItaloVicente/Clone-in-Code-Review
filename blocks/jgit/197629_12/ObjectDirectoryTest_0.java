	@Test
	public void testWindowCursorGetCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		WindowCursor curs = new WindowCursor(db.getObjectDatabase());
		assertTrue(curs.getCommitGraph().isEmpty());
		commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		assertTrue(curs.getCommitGraph().isPresent());

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH

		assertTrue(curs.getCommitGraph().isEmpty());
	}

