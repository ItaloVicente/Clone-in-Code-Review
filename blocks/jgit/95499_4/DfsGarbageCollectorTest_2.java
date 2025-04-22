		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(GC
		assertTrue("has commit0"
		assertTrue("has commit1"
	}

	@Test
	public void testCollectionWithPureGarbageAndGarbagePacksPurged()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(UNREACHABLE_GARBAGE
		assertTrue("has commit0"
		assertTrue("has commit1"

		gcWithTtl();
		assertEquals(0
	}

	@Test
	public void testCollectionWithPureGarbageAndRereferencingGarbage()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		DfsPackDescription pack = packs[0].getPackDescription();
		assertEquals(UNREACHABLE_GARBAGE
		assertTrue("has commit0"
		assertTrue("has commit1"

		git.update("master"

		gcWithTtl();
		packs = odb.getPacks();
		assertEquals(1

		pack = packs[0].getPackDescription();
		assertEquals(GC
		assertTrue("has commit0"
		assertFalse("no commit1"
