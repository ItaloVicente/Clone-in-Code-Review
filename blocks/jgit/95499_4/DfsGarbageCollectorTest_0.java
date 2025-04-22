		assertTrue("gc pack found"
		assertTrue("garbage pack found"

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(GC
		assertTrue("has commit0"
		assertFalse("no commit1"
	}

	@Test
	public void testCollectionWithGarbageAndRereferencingGarbage()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"
