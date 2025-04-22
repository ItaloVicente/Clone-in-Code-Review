	@Test
	public void testGetCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		ObjectDirectory dir = db.getObjectDatabase();
		assertTrue(dir.getCommitGraph().isEmpty());

		commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		File file = new File(db.getObjectsDirectory()
				Constants.INFO_COMMIT_GRAPH);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertTrue(dir.getCommitGraph().isPresent());
		assertEquals(1

		commitFile("file2.txt"
		gc.gc();
		assertEquals(2

		file.delete();
		assertFalse(file.exists());
		assertTrue(dir.getCommitGraph().isEmpty());

		try (PrintWriter writer = new PrintWriter(file
			writer.println("this is a corrupt commit-graph");
		}
		assertTrue(dir.getCommitGraph().isEmpty());

		gc.gc();
		assertTrue(dir.getCommitGraph().isPresent());
		assertEquals(2
	}

