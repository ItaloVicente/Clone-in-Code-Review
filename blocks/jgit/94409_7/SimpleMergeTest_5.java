	@Test
	public void testTheirs_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.THEIRS.newMerger(db);
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
			assertTrue(merge);
			assertEquals(db.resolve("c^{tree}")
		}
	}

