	@Test
	public void testStageState() throws IOException {
		final int base = DirCacheEntry.STAGE_1;
		final int ours = DirCacheEntry.STAGE_2;
		final int theirs = DirCacheEntry.STAGE_3;
		verifyStageState(StageState.BOTH_DELETED
		verifyStageState(StageState.DELETED_BY_THEM
		verifyStageState(StageState.DELETED_BY_US
		verifyStageState(StageState.BOTH_MODIFIED
		verifyStageState(StageState.ADDED_BY_US
		verifyStageState(StageState.BOTH_ADDED
		verifyStageState(StageState.ADDED_BY_THEM

		assertTrue(StageState.BOTH_DELETED.hasBase());
		assertFalse(StageState.BOTH_DELETED.hasOurs());
		assertFalse(StageState.BOTH_DELETED.hasTheirs());
		assertFalse(StageState.BOTH_ADDED.hasBase());
		assertTrue(StageState.BOTH_ADDED.hasOurs());
		assertTrue(StageState.BOTH_ADDED.hasTheirs());
	}

	private void verifyStageState(StageState expected
			throws IOException {
		DirCacheBuilder builder = db.lockDirCache().builder();
		for (int stage : stages) {
			DirCacheEntry entry = createEntry("a"
					stage
			builder.add(entry);
		}
		builder.commit();

		IndexDiff diff = new IndexDiff(db
				new FileTreeIterator(db));
		diff.diff();

		assertEquals(
				"Conflict for entries in stages " + Arrays.toString(stages)
				expected
	}

