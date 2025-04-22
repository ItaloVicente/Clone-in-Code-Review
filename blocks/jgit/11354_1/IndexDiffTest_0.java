	@Test
	public void testConflictTypes() throws IOException {
		final int base = DirCacheEntry.STAGE_1;
		final int ours = DirCacheEntry.STAGE_2;
		final int theirs = DirCacheEntry.STAGE_3;
		verifyConflict(ConflictType.BOTH_DELETED
		verifyConflict(ConflictType.DELETED_BY_THEM
		verifyConflict(ConflictType.DELETED_BY_US
		verifyConflict(ConflictType.BOTH_MODIFIED
		verifyConflict(ConflictType.ADDED_BY_US
		verifyConflict(ConflictType.BOTH_ADDED
		verifyConflict(ConflictType.ADDED_BY_THEM

		assertTrue(ConflictType.BOTH_DELETED.hasBase());
		assertFalse(ConflictType.BOTH_DELETED.hasOurs());
		assertFalse(ConflictType.BOTH_DELETED.hasTheirs());
		assertFalse(ConflictType.BOTH_ADDED.hasBase());
		assertTrue(ConflictType.BOTH_ADDED.hasOurs());
		assertTrue(ConflictType.BOTH_ADDED.hasTheirs());
	}

	private void verifyConflict(ConflictType expected
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

