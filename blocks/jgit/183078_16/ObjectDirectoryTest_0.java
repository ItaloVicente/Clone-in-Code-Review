	@Test
	public void testCommitGraphFile() throws Exception {
		ObjectDirectory dir = db.getObjectDatabase();
		assertNull(dir.getCommitGraph());

		commitFile("file.txt"
		GC gc = new GC(db);
		gc.writeCommitGraph();

		assertNotNull(dir.getCommitGraph());
	}

