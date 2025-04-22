	@Test
	public void testGetCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		ObjectDirectory dir = db.getObjectDatabase();
		assertNull(dir.getCommitGraph());

		commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		File file = new File(db.getObjectsDirectory()
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertNotNull(dir.getCommitGraph());
		assertEquals(1

		commitFile("file2.txt"
		gc.gc();
		assertEquals(2

		file.delete();
		assertFalse(file.exists());
		assertNull(dir.getCommitGraph());

		try (PrintWriter writer = new PrintWriter(file
			writer.println("this is a corrupt commit-graph");
		}
		assertNull(dir.getCommitGraph());
	}

