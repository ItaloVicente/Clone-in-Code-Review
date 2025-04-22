	@Test
	public void testTrivialTwoWay_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(ins
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a^0^0^0")
			assertTrue(merge);
			assertEquals(db.resolve("a^0^0^{tree}")
		}
	}

