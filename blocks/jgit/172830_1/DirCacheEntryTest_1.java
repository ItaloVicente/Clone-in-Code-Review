	@Test
	public void testSetStage() {
		DirCacheEntry e = new DirCacheEntry("some/path"
		e.setAssumeValid(true);
		e.setCreationTime(2L);
		e.setFileMode(FileMode.EXECUTABLE_FILE);
		e.setLastModified(EPOCH.plusMillis(3L));
		e.setLength(100L);
		e.setObjectId(ObjectId
				.fromString("0123456789012345678901234567890123456789"));
		e.setUpdateNeeded(true);
		e.setStage(DirCacheEntry.STAGE_2);

		assertTrue(e.isAssumeValid());
		assertEquals(2L
		assertEquals(
				ObjectId.fromString("0123456789012345678901234567890123456789")
				e.getObjectId());
		assertEquals(FileMode.EXECUTABLE_FILE
		assertEquals(EPOCH.plusMillis(3L)
		assertEquals(100L
		assertEquals(DirCacheEntry.STAGE_2
		assertTrue(e.isUpdateNeeded());
		assertEquals("some/path"

		e.setStage(DirCacheEntry.STAGE_0);

		assertTrue(e.isAssumeValid());
		assertEquals(2L
		assertEquals(
				ObjectId.fromString("0123456789012345678901234567890123456789")
				e.getObjectId());
		assertEquals(FileMode.EXECUTABLE_FILE
		assertEquals(EPOCH.plusMillis(3L)
		assertEquals(100L
		assertEquals(DirCacheEntry.STAGE_0
		assertTrue(e.isUpdateNeeded());
		assertEquals("some/path"
	}

