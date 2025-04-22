	@Test
	public void testCommitGraphFile() throws Exception {
		ObjectDirectory dir = db.getObjectDatabase();
		assertNull(dir.getCommitGraph());

		commitFile("file.txt"
		
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH
		GC gc = new GC(db);
		gc.gc();

		assertNotNull(dir.getCommitGraph());
	}

