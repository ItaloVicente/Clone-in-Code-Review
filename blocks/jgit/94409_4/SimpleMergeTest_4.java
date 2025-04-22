	@Test
	public void testOurs_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.OURS.newMerger(ins
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
			assertTrue(merge);
			assertEquals(db.resolve("a^{tree}")
		}
	}

